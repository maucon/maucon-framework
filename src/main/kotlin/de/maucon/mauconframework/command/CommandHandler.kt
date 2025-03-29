package de.maucon.mauconframework.command

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandHandler(
    val priority: Int = Int.MAX_VALUE,
    val ignoreCancelled: Boolean = false
)