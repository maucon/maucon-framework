package de.maucon.mauconframework.di

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Initialize
import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Qualifier
import de.maucon.mauconframework.di.exception.*
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Parameter

private val log = LoggerFactory.getLogger(DependencyInjector::class.java)

internal object DependencyInjector {
    internal fun instantiateClasses(startingClass: Class<*>): Collection<Any> {
        val reflection = Reflections(startingClass)

        val injectableClasses = scanForAnnotatedClasses(reflection, Injectable::class.java)
        val configurationClasses = scanForAnnotatedClasses(reflection, Configuration::class.java)
        log.debug("Found ${injectableClasses.size} injectable classes")
        log.debug("Found ${configurationClasses.size} configuration classes")

        val componentsDescriptors = mapClassToComponentDescriptors(injectableClasses, configurationClasses)
        val components = instantiateComponents(componentsDescriptors)
        log.debug("Resolved ${components.size} components")

        val initializeCalls = gatherInitializeCalls(components)
        log.debug("Running ${initializeCalls.size} initialize calls")

        runInitializeCalls(initializeCalls)

        return components.values
    }

    private fun scanForAnnotatedClasses(
        reflection: Reflections,
        annotationClass: Class<*>
    ): Set<Class<*>> {
        val query = Scanners.TypesAnnotated.with(annotationClass)
        return reflection.get(query)
            .map { Class.forName(it) }
            .filter { !it.isInterface && !it.isAnnotation }
            .toSet()
    }

    private fun mapClassToComponentDescriptors(
        injectableClasses: Set<Class<*>>,
        configurationClasses: Set<Class<*>>
    ): List<ComponentDescriptor> =
        injectableClasses.map { mapClassToComponentDescriptor(it) } +
                configurationClasses.flatMap { mapMethodsToComponentDescriptor(it) }

    private fun mapClassToComponentDescriptor(clazz: Class<*>): ComponentDescriptor {
        val qualifierName = clazz.getAnnotation(Injectable::class.java)?.name?.ifEmpty { clazz.simpleName }?.lowercase()
            ?: clazz.simpleName.lowercase()
        val classConstructor = clazz.constructors.first() // what when multiple constructors are found?

        val constructor = { args: Array<*> -> classConstructor.newInstance(*args) }
        val qualifier = ComponentQualifier(clazz, qualifierName)
        val dependencies = mapMethodParametersToComponentQualifier(classConstructor.parameters.asList())

        return ComponentDescriptor(qualifier, dependencies, constructor)
    }

    private fun mapMethodsToComponentDescriptor(clazz: Class<*>): List<ComponentDescriptor> {
        val classComponentDescriptor = mapClassToComponentDescriptor(clazz)
        val componentDescriptors = listOf(classComponentDescriptor)

        val methodComponentDescriptors = clazz.declaredMethods
            .filter { it.isAnnotationPresent(Injectable::class.java) }
            .map { mapMethodToComponentDescriptor(it, classComponentDescriptor) }

        return componentDescriptors + methodComponentDescriptors
    }

    private fun mapMethodToComponentDescriptor(
        method: Method,
        classComponentDescriptor: ComponentDescriptor
    ): ComponentDescriptor {
        val injectable = method.getAnnotation(Injectable::class.java)
        val qualifierName = injectable.name.ifEmpty { method.name }.lowercase()

        val qualifier = ComponentQualifier(method.returnType, qualifierName)
        val dependencies = listOf(classComponentDescriptor.qualifier) + mapMethodParametersToComponentQualifier(method.parameters.asList())
        val constructor = { args: Array<*> ->
            val (classObject, realArgs) = args[0] to args.drop(1).toTypedArray()
            method.invoke(classObject, *realArgs)
        }

        return ComponentDescriptor(qualifier, dependencies, constructor)
    }

    private fun mapMethodParametersToComponentQualifier(parameters: List<Parameter>) =
        parameters.map { mapParameterToComponentQualifier(it) }

    private fun mapParameterToComponentQualifier(parameter: Parameter): ComponentQualifier {
        val qualifierName = parameter.getAnnotation(Qualifier::class.java)?.name?.lowercase()
            ?: parameter.type.simpleName.lowercase()

        return ComponentQualifier(parameter.type, qualifierName)
    }

    private fun instantiateComponents(componentDescriptors: List<ComponentDescriptor>): MutableMap<ComponentQualifier, Any> {
        val instances = mutableMapOf<ComponentQualifier, Any>()
        val resolving = mutableSetOf<ComponentQualifier>()

        for (descriptor in componentDescriptors) {
            try {
                resolveComponent(componentDescriptors, instances, resolving, descriptor)
            } catch (e: Exception) {
                throw ResolveComponentException("Error resolving component: ${descriptor.qualifier}. Cause: ${e.message}", e)
            }
        }

        return instances
    }

    private fun resolveComponent(
        componentDescriptors: List<ComponentDescriptor>,
        instances: MutableMap<ComponentQualifier, Any>,
        resolving: MutableSet<ComponentQualifier>,
        descriptor: ComponentDescriptor
    ): Any {
        val qualifier = descriptor.qualifier
        if (qualifier in instances) {
            return instances[qualifier]!!
        }

        log.debug("Instantiating component: ${descriptor.qualifier.type.simpleName}")

        if (qualifier in resolving) {
            throw CyclicDependencyException("Cyclic dependency detected for component: $qualifier")
        }

        resolving.add(qualifier)

        val resolvedDependencies = descriptor.dependencies.map {
            val dependencyDescriptor = findMatchingComponentDescriptor(componentDescriptors, it)
                ?: throw UnresolvedDependencyException("Unresolved dependency: $it required by $qualifier")

            resolveComponent(componentDescriptors, instances, resolving, dependencyDescriptor)
        }.toTypedArray()

        val instance = try {
            descriptor.constructor(resolvedDependencies)
        } catch (e: Exception) {
            throw ComponentInstantiationException("Failed to instantiate component: $qualifier", e)
        }

        instances[qualifier] = instance
        resolving.remove(qualifier)

        return instance
    }

    private fun gatherInitializeCalls(components: MutableMap<ComponentQualifier, Any>): List<() -> Any> =
        components.values.flatMap { component ->
            component::class.java.declaredMethods
                .filter { it.isAnnotationPresent(Initialize::class.java) }
                .map { method ->
                    val parameters = method.parameters
                        .map { resolveMethodParameter(it, components) }
                        .toTypedArray()

                    method.isAccessible = true
                    { method.invoke(component, *parameters) }
                }
        }

    private fun resolveMethodParameter(
        parameter: Parameter,
        components: MutableMap<ComponentQualifier, Any>
    ): Any? {
        val parameterComponentQualifier = mapParameterToComponentQualifier(parameter)
        val matchingQualifier = findMatchingComponentQualifier(components.keys, parameterComponentQualifier)

        return components[matchingQualifier]
    }

    private fun runInitializeCalls(initializeCalls: List<() -> Any>) {
        for (call in initializeCalls) {
            try {
                call()
            } catch (e: InvocationTargetException) {
                throw InitializeMethodInvocationException("Failed to invoke @Initialize method", e)
            }
        }
    }

    private fun findMatchingComponentDescriptor(
        componentDescriptors: Collection<ComponentDescriptor>,
        inputQualifier: ComponentQualifier,
    ): ComponentDescriptor? {
        componentDescriptors.find { it.qualifier == inputQualifier }
            ?.also { return it }

        componentDescriptors.find { it.qualifier.type == inputQualifier.type }
            ?.also { return it }

        return null
    }

    private fun findMatchingComponentQualifier(
        componentDescriptors: Collection<ComponentQualifier>,
        inputQualifier: ComponentQualifier,
    ): ComponentQualifier? {
        componentDescriptors.find { it == inputQualifier }
            ?.also { return it }

        componentDescriptors.find { it.type == inputQualifier.type }
            ?.also { return it }

        return null
    }
}