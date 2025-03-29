package de.maucon.mauconframework.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import org.slf4j.LoggerFactory

object EventBus {
    private val log = LoggerFactory.getLogger(EventBus::class.java)
    private val flow = MutableSharedFlow<Any>()

    suspend fun <T : Any> publish(event: T) {
        flow.emit(event)
    }

    suspend fun <T : Any> subscribe(subscriberData: EventSubscriberData<T>) {
        flow
            .filter { subscriberData.eventType.isInstance(it) }
            .collect {
                try {
                    @Suppress("UNCHECKED_CAST")
                    subscriberData.collector(it as T)
                } catch (e: Exception) {
                    with(subscriberData) {
                        log.error(
                            "Got exception in event subscriber '${subscriberMethodName}(${eventType})' of ${subscriberClassType.simpleName} (${subscriberClassType})",
                            e.cause
                        )
                    }
                }
            }
    }
}