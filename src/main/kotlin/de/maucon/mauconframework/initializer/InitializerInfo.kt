package de.maucon.mauconframework.initializer

import de.maucon.mauconframework.di.data.ComponentDefinition

internal data class InitializerInfo(
    val componentDefinition: ComponentDefinition,
    val priority: Int,
    val methodName: String,
    val runnable: () -> Any,
)