package de.maucon.mauconframework.command

/**
 * Annotation for marking methods as command handlers.
 *
 * Methods annotated with `@CommandHandler` will be automatically registered
 * to handle commands of the specified type.
 *
 * @property priority Defines the execution priority of the handler.
 *                    Lower values indicate higher priority.
 * @property ignoreCancelled If `true`, the handler will ignore cancelled commands.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandHandler(
    val priority: Int = Int.MAX_VALUE,
    val ignoreCancelled: Boolean = false
)