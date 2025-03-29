package de.maucon.mauconframework.initializer

import de.maucon.mauconframework.di.ComponentDefinitionCreator.mapParameterToDependencyQualifier
import de.maucon.mauconframework.di.DependencyMatcher.getComponentDefinitionByDependencyQualifier
import de.maucon.mauconframework.di.data.ComponentDefinition
import de.maucon.mauconframework.initializer.exception.InitializeMethodInvocationException
import de.maucon.mauconframework.initializer.exception.InitializeMethodUnresolvedDependencyException
import org.slf4j.LoggerFactory
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Parameter

internal object InitializerService {
    private val log = LoggerFactory.getLogger(InitializerService::class.java)

    internal fun runInitializers(components: Map<ComponentDefinition, Any>) {
        val initializerInfos = gatherInitializerInfo(components)
        log.debug("Found ${initializerInfos.size} initializer")

        initializerInfos
            .sortedBy { it.priority }
            .forEach { info ->
                log.debug("Running initializer '{}' of {}", info.methodName, info.componentDefinition)

                try {
                    info.runnable()
                } catch (e: InvocationTargetException) {
                    throw InitializeMethodInvocationException("Failed to invoke @${Initializer::class.simpleName} method '${info.methodName}' of component ${info.componentDefinition}", e)
                }
            }
    }

    private fun gatherInitializerInfo(components: Map<ComponentDefinition, Any>): List<InitializerInfo> =
        components.flatMap { (componentDefinition, instance) ->
            componentDefinition.type.methods
                .filter { it.isAnnotationPresent(Initializer::class.java) }
                .map { mapToInitializerInfo(it, componentDefinition, components, instance) }
        }

    private fun mapToInitializerInfo(
        method: Method,
        componentDefinition: ComponentDefinition,
        components: Map<ComponentDefinition, Any>,
        instance: Any
    ): InitializerInfo {
        val initializer = method.getAnnotation(Initializer::class.java)!!
        val parameters = method.parameters
            .map { resolveMethodParameter(method.name, componentDefinition, it, components) }
            .toTypedArray()

        method.isAccessible = true
        val runnable = { method.invoke(instance, *parameters) }

        return InitializerInfo(componentDefinition, initializer.priority, method.name, runnable)
    }

    private fun resolveMethodParameter(
        methodName: String,
        componentDefinition: ComponentDefinition,
        parameter: Parameter,
        components: Map<ComponentDefinition, Any>
    ): Any? {
        val dependencyQualifier = mapParameterToDependencyQualifier(parameter)
        val matchingQualifier = getComponentDefinitionByDependencyQualifier(componentDefinition, components.keys, dependencyQualifier)
            ?: throw InitializeMethodUnresolvedDependencyException("Unresolved dependency $dependencyQualifier for @${Initializer::class.simpleName} method '$methodName' of component $componentDefinition")

        return components[matchingQualifier]
    }
}
