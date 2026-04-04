package de.maucon.mauconframework.command

import de.maucon.mauconframework.command.exception.CommandExecutionException

/**
 * A gateway for dispatching commands through the [CommandBus].
 *
 * This object serves as the primary entry point for command execution,
 * abstracting the underlying dispatching mechanism and providing a
 * consistent API for invoking command handlers.
 *
 */
object CommandGateway {
    /**
     * Dispatches the given [command] to the [CommandBus] for handling.
     *
     * The command is processed synchronously by its corresponding handlers,
     * and the (potentially modified) command instance is returned upon
     * successful execution.
     *
     * If an exception occurs during command handling, it is caught and
     * wrapped in a [CommandExecutionException], which is then thrown to
     * the caller.
     *
     * @param command The command instance to be processed.
     * @return The processed command instance.
     *
     * @throws CommandExecutionException if any exception occurs during
     * command handler execution.
     */
    fun <T : Any> apply(command: T): T {
        return CommandBus.dispatch(command)
    }
}