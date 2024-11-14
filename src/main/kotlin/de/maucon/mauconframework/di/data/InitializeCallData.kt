package de.maucon.mauconframework.di.data

internal data class InitializeCallData(
    val componentDefinition: ComponentDefinition,
    val methodName: String,
    val runnable: () -> Any,
)