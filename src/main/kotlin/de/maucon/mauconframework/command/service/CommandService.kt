package de.maucon.mauconframework.command.service

import de.maucon.mauconframework.command.CommandBus
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.command.CommandHandlerData
import de.maucon.mauconframework.di.data.ComponentDefinition
import org.slf4j.LoggerFactory
import java.lang.reflect.Method

internal object CommandService {
    private val log = LoggerFactory.getLogger(CommandService::class.java)

    internal fun registerCommands(components: Map<ComponentDefinition, Any>) {
        val handlerInfos = gatherCommandHandlerInfos(components)
        log.debug("Registering ${handlerInfos.size} command handler")

        handlerInfos.forEach { info ->
            log.debug("Register handler for '{}' in '{}' of {}", info.commandType.simpleName, info.methodName, info.componentDefinition)

            info.registerHandler()
        }
    }

    private fun gatherCommandHandlerInfos(components: Map<ComponentDefinition, Any>): List<CommandHandlerInfo> =
        components.flatMap { (componentDefinition, instance) ->
            componentDefinition.type.methods
                .filter { it.isAnnotationPresent(CommandHandler::class.java) }
                .map { mapToCommandHandlerInfo(it, componentDefinition, instance) }
        }

    private fun mapToCommandHandlerInfo(
        method: Method,
        componentDefinition: ComponentDefinition,
        instance: Any
    ): CommandHandlerInfo {
        val annotation = method.getAnnotation(CommandHandler::class.java)!!

        val parameters = method.parameterTypes
        if (parameters.size != 1) {
            throw RuntimeException("TODO ") // TODO
        }

        @Suppress("UNCHECKED_CAST")
        val eventType = parameters.first() as Class<Any>

        method.isAccessible = true


        val registerHandler = {
            val handlerData = CommandHandlerData(eventType, annotation.priority, annotation.ignoreCancelled) { method.invoke(instance, it) }
            CommandBus.register(handlerData)
        }

        return CommandHandlerInfo(componentDefinition, method.name, eventType, registerHandler)
    }
}