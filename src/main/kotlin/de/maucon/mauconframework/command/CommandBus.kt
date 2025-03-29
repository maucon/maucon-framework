package de.maucon.mauconframework.command

import de.maucon.mauconframework.command.cancellable.Cancellable

object CommandBus {
    private val handlers = mutableListOf<CommandHandlerData<out Any>>()

    fun <T : Any> dispatch(command: T): T { // TODO clean code
        val commandHandlers = handlers
            .filter { it.commandType.isAssignableFrom(command::class.java) }
            .sortedBy { it.priority }

        if (command is Cancellable) {
            var isCancelled = command.isCancelled()

            for (handler in commandHandlers) {
                if (isCancelled && !handler.ignoreCancelled) continue

                @Suppress("UNCHECKED_CAST")
                (handler as CommandHandlerData<T>).invoke(command)

                isCancelled = command.isCancelled()
            }
        } else {
            commandHandlers.forEach {
                @Suppress("UNCHECKED_CAST")
                (it as CommandHandlerData<T>).invoke(command)
            }
        }

        return command
    }

    fun <T : Any> register(handlerData: CommandHandlerData<T>) {
        handlers.add(handlerData)
    }
}