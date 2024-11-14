package de.maucon.mauconframework.di

import de.maucon.mauconframework.di.data.ComponentDefinition
import de.maucon.mauconframework.di.data.DependencyQualifier
import de.maucon.mauconframework.di.exception.AmbiguousDependencyException

internal object DependencyMatcher {
    internal fun getComponentDefinitionByDependencyQualifier(
        parentComponentDefinition: ComponentDefinition,
        componentDefinitions: Collection<ComponentDefinition>,
        dependencyQualifier: DependencyQualifier,
    ): ComponentDefinition? {
        val fullMatches = componentDefinitions
            .filter { it.type == dependencyQualifier.type && it.name == dependencyQualifier.name }

        if (fullMatches.size == 1) return fullMatches.first()
        if (fullMatches.size > 1) {
            throw AmbiguousDependencyException("Multiple components found for dependency $dependencyQualifier for component $parentComponentDefinition.")
        }

        val typeMatches = componentDefinitions.filter { it.type == dependencyQualifier.type }

        if (typeMatches.size == 1) return typeMatches.first()
        if (typeMatches.size > 1) {
            throw AmbiguousDependencyException("Multiple components found for dependency by type $dependencyQualifier for component $parentComponentDefinition. Specify a matching unique name with @Qualifier.")
        }

        return null
    }
}