package de.maucon.mauconframework.di

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Qualifier
import de.maucon.mauconframework.di.exception.ComponentInstantiationException
import de.maucon.mauconframework.di.exception.CyclicDependencyException
import de.maucon.mauconframework.di.exception.ResolveComponentException
import de.maucon.mauconframework.di.exception.UnresolvedDependencyException
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.slf4j.LoggerFactory
import java.lang.reflect.Method
import java.lang.reflect.Parameter

private val log = LoggerFactory.getLogger(DependencyInjector::class.java)

internal object DependencyInjector {
    internal fun instantiateClasses(startingClass: Class<*>): Collection<Any> {
        val reflection = Reflections(startingClass)

        val injectableClasses = scanForClassesWithAnnotation(reflection, Injectable::class.java)
        val configurationClasses = scanForClassesWithAnnotation(reflection, Configuration::class.java)

        val componentsDescriptors = mapToComponentDescriptors(injectableClasses, configurationClasses)
        log.debug(componentsDescriptors.toString())

        return instantiateComponents(componentsDescriptors)
    }

    private fun scanForClassesWithAnnotation(
        reflection: Reflections,
        annotationClass: Class<*>
    ): Set<Class<*>> {
        val query = Scanners.TypesAnnotated.with(annotationClass)

        return reflection.get(query)
            .map { Class.forName(it) }
            .filter { !it.isInterface && !it.isAnnotation }
            .toSet()
    }

    private fun mapToComponentDescriptors(
        injectableClasses: Set<Class<*>>,
        configurationClasses: Set<Class<*>>
    ): List<ComponentDescriptor> =
        injectableClasses.map { mapClassToComponentDescriptor(it) } +
                configurationClasses.flatMap { mapMethodsToComponentDescriptor(it) }

    private fun mapClassToComponentDescriptor(clazz: Class<*>): ComponentDescriptor {
        val qualifierName = clazz.getAnnotation(Injectable::class.java)?.name?.ifEmpty { clazz.simpleName }?.lowercase()
            ?: clazz.simpleName.lowercase()
        val classConstructor = clazz.constructors.first() // TODO what when multiple constructors are found?

        val constructor = { args: Array<*> -> classConstructor.newInstance(*args) }
        val qualifier = ComponentQualifier(clazz, qualifierName)
        val dependencies = mapMethodParameterToComponentQualifier(classConstructor.parameters.asList())

        return ComponentDescriptor(qualifier, dependencies, constructor)
    }

    private fun mapMethodsToComponentDescriptor(clazz: Class<*>): List<ComponentDescriptor> {
        val classComponentDescriptor = mapClassToComponentDescriptor(clazz)
        val componentDescriptors = listOf(classComponentDescriptor)

        val methodComponentDescriptors = clazz.methods
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
        val dependencies = listOf(classComponentDescriptor.qualifier) + mapMethodParameterToComponentQualifier(method.parameters.asList())
        val constructor = { args: Array<*> ->
            val (classObject, realArgs) = args[0] to args.drop(1).toTypedArray()
            method.invoke(classObject, *realArgs)
        }

        return ComponentDescriptor(qualifier, dependencies, constructor)
    }

    private fun mapMethodParameterToComponentQualifier(parameters: List<Parameter>) =
        parameters.map { parameter ->
            val qualifierName = parameter.getAnnotation(Qualifier::class.java)?.name?.lowercase()
                ?: parameter.type.simpleName.lowercase()

            ComponentQualifier(parameter.type, qualifierName)
        }

    private fun instantiateComponents(componentDescriptors: List<ComponentDescriptor>): Collection<Any> {
        val instances = mutableMapOf<ComponentQualifier, Any>()
        val resolving = mutableSetOf<ComponentQualifier>()

        fun resolveComponent(descriptor: ComponentDescriptor): Any {
            val qualifier = descriptor.qualifier
            if (qualifier in instances) {
                return instances[qualifier]!!
            }
            if (qualifier in resolving) {
                throw CyclicDependencyException("Cyclic dependency detected for component: ${qualifier.type.simpleName}")
            }

            resolving.add(qualifier)

            val resolvedDependencies = descriptor.dependencies.map { dependencyQualifier ->
                val dependencyDescriptor = componentDescriptors.find { it.qualifier == dependencyQualifier }
                    ?: throw UnresolvedDependencyException("Unresolved dependency: ${dependencyQualifier.type.simpleName} required by ${qualifier.type.simpleName}")

                resolveComponent(dependencyDescriptor)
            }.toTypedArray()

            val instance = try {
                descriptor.constructor(resolvedDependencies)
            } catch (e: Exception) {
                throw ComponentInstantiationException("Failed to instantiate component: ${qualifier.type}", e)
            }

            instances[qualifier] = instance
            resolving.remove(qualifier)

            return instance
        }

        for (descriptor in componentDescriptors) {
            try {
                resolveComponent(descriptor)
            } catch (e: Exception) {
                throw ResolveComponentException("Error instantiating component: ${descriptor.qualifier.type.simpleName}. Cause: ${e.message}", e)
            }
        }

        return instances.values
    }
}