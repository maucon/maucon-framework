package de.maucon.mauconframework.command

/**
 * Annotation for marking methods as command handlers.
 *
 * Methods annotated with [CommandHandler] are automatically registered
 * to handle commands of the type matching the method’s single parameter.
 * The handler will be invoked whenever a corresponding command is dispatched
 * via the [CommandBus] or [CommandGateway].
 *
 * ### Properties
 * - [priority]: Defines the execution priority of the handler.
 *   Handlers with **lower values are executed first**. This allows multiple
 *   handlers for the same command type to be invoked in a controlled order.
 *
 * - [ignoreCancelled]: If `true`, the handler will **not be executed**
 *   for commands that have been marked as cancelled. This can be used
 *   to avoid processing commands that were invalidated earlier in the pipeline.
 *
 * ### Requirements
 * - The annotated method must accept exactly **one parameter**: the command type it handles.
 * - The method should not return a value (it will be ignored).
 *
 * ### Example
 * ```kotlin
 * class UserCommandHandler {
 *
 *     @CommandHandler(priority = 0)
 *     fun handleCreateUser(command: CreateUserCommand) {
 *         println("High-priority handler: creating user ${command.userId}")
 *     }
 *
 *     @CommandHandler(priority = 10, ignoreCancelled = true)
 *     fun logUserCommand(command: CreateUserCommand) {
 *         println("Logging command for user ${command.userId}")
 *     }
 * }
 * ```
 *
 * @property priority Execution order for handlers; lower values run first.
 * @property ignoreCancelled If true, the handler will skip cancelled commands.
 *
 * @see CommandGateway
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandHandler(
    val priority: Int = Int.MAX_VALUE,
    val ignoreCancelled: Boolean = false
)