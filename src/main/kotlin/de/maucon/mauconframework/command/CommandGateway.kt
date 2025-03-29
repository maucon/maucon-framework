package de.maucon.mauconframework.command

object CommandGateway {
    fun <T : Any> apply(command: T): T {
        return CommandBus.dispatch(command)
    }
}