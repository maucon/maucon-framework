package de.maucon.mauconframework.di

import de.maucon.mauconframework.annotation.Initialize
import de.maucon.mauconframework.di.ComponentDefinitionCreator.mapParameterToDependencyQualifier
import de.maucon.mauconframework.di.DependencyMatcher.getComponentDefinitionByDependencyQualifier
import de.maucon.mauconframework.di.data.ComponentDefinition
import de.maucon.mauconframework.di.data.InitializeCallData
import de.maucon.mauconframework.di.exception.InitializeMethodInvocationException
import de.maucon.mauconframework.di.exception.InitializeMethodUnresolvedDependencyException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Parameter

internal object InitializeRunner {
    internal fun gatherInitializeCallData(
        components: Map<ComponentDefinition, Any>
    ): List<InitializeCallData> =
        components.flatMap { (componentDefinition, instance) ->
            componentDefinition.type.declaredMethods
                .filter { it.isAnnotationPresent(Initialize::class.java) }
                .map { mapToInitializeCallData(it, componentDefinition, components, instance) }
        }

    private fun mapToInitializeCallData(
        method: Method,
        componentDefinition: ComponentDefinition,
        components: Map<ComponentDefinition, Any>,
        instance: Any
    ): InitializeCallData {
        val parameters = method.parameters
            .map { resolveMethodParameter(method.name, componentDefinition, it, components) }
            .toTypedArray()

        method.isAccessible = true
        val runnable = { method.invoke(instance, *parameters) }

        return InitializeCallData(componentDefinition, method.name, runnable)
    }

    private fun resolveMethodParameter(
        methodName: String,
        componentDefinition: ComponentDefinition,
        parameter: Parameter,
        components: Map<ComponentDefinition, Any>
    ): Any? {
        val dependencyQualifier = mapParameterToDependencyQualifier(parameter)
        val matchingQualifier = getComponentDefinitionByDependencyQualifier(componentDefinition, components.keys, dependencyQualifier)
            ?: throw InitializeMethodUnresolvedDependencyException("Unresolved dependency $dependencyQualifier for @Initialize method '$methodName' of component $componentDefinition")

        return components[matchingQualifier]
    }

    internal fun runInitializeCalls(initializeCallData: List<InitializeCallData>) {
        for (callData in initializeCallData) {
            try {
                callData.runnable()
            } catch (e: InvocationTargetException) {
                throw InitializeMethodInvocationException("Failed to invoke @Initialize method '${callData.methodName}' of component ${callData.componentDefinition}", e)
            }
        }
    }
}