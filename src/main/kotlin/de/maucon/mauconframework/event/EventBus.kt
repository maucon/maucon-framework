package de.maucon.mauconframework.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter

object EventBus {
    private val flow = MutableSharedFlow<Any>()

    suspend fun <T : Any> publish(event: T) {
        flow.emit(event)
    }

    suspend fun <T : Any> subscribe(
        eventType: Class<T>,
        collector: suspend (T) -> Unit
    ) {
        flow
            .filter { eventType.isInstance(it) }
            .collect {
                @Suppress("UNCHECKED_CAST")
                collector(it as T)
            }
    }
}