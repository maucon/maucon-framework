package de.maucon.mauconframework.command

data class CommandHandlerData<T>(
    val handlerClassType: Class<*>,
    val handlerMethodName: String,
    val commandType: Class<T>,
    val priority: Int,
    val ignoreCancelled: Boolean,
    val handler: (T) -> Unit
) {
    fun invoke(data: T) = handler(data)
}