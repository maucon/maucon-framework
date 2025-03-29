package de.maucon.mauconframework.command

import de.maucon.mauconframework.command.cancellable.Cancellable
import org.slf4j.LoggerFactory

object CommandBus {
    private val log = LoggerFactory.getLogger(CommandBus::class.java)
    private val handlers = mutableListOf<CommandHandlerData<out Any>>()

    fun <T : Any> dispatch(command: T): T {
        val commandHandlers = handlers
            .filter { it.commandType.isAssignableFrom(command::class.java) }
            .sortedBy { it.priority }

        if (command is Cancellable) {
            invokeCancellableCommandHandlers(command, commandHandlers)
        } else {
            invokeCommandHandlers(commandHandlers, command)
        }

        return command
    }

    fun <T : Any> register(handlerData: CommandHandlerData<T>) {
        handlers.add(handlerData)
    }

    private fun <T : Cancellable> invokeCancellableCommandHandlers(
        command: T,
        commandHandlers: List<CommandHandlerData<out Any>>
    ) {
        var isCancelled = command.isCancelled()

        for (handler in commandHandlers) {
            if (isCancelled && handler.ignoreCancelled) continue

            @Suppress("UNCHECKED_CAST")
            invokeCommandHandler((handler as CommandHandlerData<T>), command)

            isCancelled = command.isCancelled()
        }
    }

    private fun <T : Any> invokeCommandHandlers(commandHandlers: List<CommandHandlerData<out Any>>, command: T) {
        commandHandlers.forEach {
            @Suppress("UNCHECKED_CAST")
            invokeCommandHandler((it as CommandHandlerData<T>), command)
        }
    }

    private fun <T> invokeCommandHandler(
        handler: CommandHandlerData<T>,
        command: T
    ) {
        try {
            handler.invoke(command)
        } catch (e: Exception) {
            log.error(
                "Got exception in command handler '${handler.handlerMethodName}(${handler.commandType})' of ${handler.handlerClassType.simpleName} (${handler.handlerClassType})",
                e.cause
            )
        }
    }
}
