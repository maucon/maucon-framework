package de.maucon.mauconframework.di

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.di.ComponentDefinitionCreator.mapConfigurationClassesToComponentDefinition
import de.maucon.mauconframework.di.ComponentDefinitionCreator.mapInjectableClassesToComponentDefinition
import de.maucon.mauconframework.di.DependencyMatcher.getComponentDefinitionByDependencyQualifier
import de.maucon.mauconframework.di.InitializeRunner.gatherInitializeCallData
import de.maucon.mauconframework.di.InitializeRunner.runInitializeCalls
import de.maucon.mauconframework.di.data.ComponentDefinition
import de.maucon.mauconframework.di.exception.ComponentInstantiationException
import de.maucon.mauconframework.di.exception.CyclicDependencyException
import de.maucon.mauconframework.di.exception.ResolveComponentException
import de.maucon.mauconframework.di.exception.UnresolvedDependencyException
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger(DependencyInjector::class.java)

internal object DependencyInjector {
    internal fun instantiateClasses(startingClass: Class<*>): Collection<Any> {
        val reflection = Reflections(startingClass)

        val injectableClasses = scanForAnnotatedClasses(reflection, Injectable::class.java)
        val configurationClasses = scanForAnnotatedClasses(reflection, Configuration::class.java)
        log.debug("Found ${injectableClasses.size} injectable classes")
        log.debug("Found ${configurationClasses.size} configuration classes")

        val injectableComponentDefinitions = mapInjectableClassesToComponentDefinition(injectableClasses)
        val configurationComponentDefinitions = mapConfigurationClassesToComponentDefinition(configurationClasses)
        val componentDefinitions = injectableComponentDefinitions + configurationComponentDefinitions
        val components = instantiateComponents(componentDefinitions)
        log.debug("Resolved ${components.size} components")

        val initializeCallData = gatherInitializeCallData(components)
        log.debug("Running ${initializeCallData.size} initialize calls")

        runInitializeCalls(initializeCallData)

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

    private fun instantiateComponents(componentDefinitions: List<ComponentDefinition>): Map<ComponentDefinition, Any> {
        val instances = mutableMapOf<ComponentDefinition, Any>()
        val resolving = mutableSetOf<ComponentDefinition>()

        for (definition in componentDefinitions) {
            try {
                resolveComponent(componentDefinitions, instances, resolving, definition)
            } catch (e: Exception) {
                throw ResolveComponentException("Error resolving component: ${definition}. Cause: ${e.message}", e)
            }
        }

        return instances
    }

    private fun resolveComponent(
        componentDefinitions: List<ComponentDefinition>,
        instances: MutableMap<ComponentDefinition, Any>,
        resolving: MutableSet<ComponentDefinition>,
        resolvingDefinition: ComponentDefinition
    ): Any {
        if (resolvingDefinition in instances) {
            return instances[resolvingDefinition]!!
        }

        log.debug("Instantiating component: {} -> {}", resolvingDefinition, resolvingDefinition.dependencies)

        if (resolvingDefinition in resolving) {
            throw CyclicDependencyException("Cyclic dependency detected for component: $resolvingDefinition")
        }

        resolving.add(resolvingDefinition)

        val resolvedDependencies = resolvingDefinition.dependencies.map {
            val dependencyComponentDefinition = getComponentDefinitionByDependencyQualifier(resolvingDefinition, componentDefinitions, it)
                ?: throw UnresolvedDependencyException("Unresolved dependency: $it required by $resolvingDefinition")

            resolveComponent(componentDefinitions, instances, resolving, dependencyComponentDefinition)
        }.toTypedArray()

        val instance = try {
            resolvingDefinition.constructor(resolvedDependencies)
        } catch (e: Exception) {
            throw ComponentInstantiationException("Failed to instantiate component: $resolvingDefinition", e)
        }

        instances[resolvingDefinition] = instance
        resolving.remove(resolvingDefinition)

        return instance
    }
}