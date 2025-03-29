package de.maucon.mauconframework.command.service

import de.maucon.mauconframework.di.data.ComponentDefinition

internal data class CommandHandlerInfo(
    val componentDefinition: ComponentDefinition,
    val methodName: String,
    val commandType: Class<*>,
    val registerHandler: () -> Unit,
)