package de.maucon.mauconframework.event

data class EventSubscriberData<T>(
    val subscriberClassType: Class<*>,
    val subscriberMethodName: String,
    val eventType: Class<T>,
    val collector: suspend (T) -> Unit
)