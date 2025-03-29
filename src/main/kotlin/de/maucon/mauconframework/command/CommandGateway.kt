package de.maucon.mauconframework.command

/**
 * A gateway for dispatching commands through the command bus.
 *
 * This object provides a method to send commands for processing via the `CommandBus`.
 */
object CommandGateway {
    /**
     * Dispatches the given command to the command bus.
     *
     * @param command The command instance to be processed.
     * @return The processed command instance.
     */
    fun <T : Any> apply(command: T): T {
        return CommandBus.dispatch(command)
    }
}