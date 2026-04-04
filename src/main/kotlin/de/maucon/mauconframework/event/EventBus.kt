package de.maucon.mauconframework.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.util.concurrent.CopyOnWriteArrayList

object EventBus {
    private val log = LoggerFactory.getLogger(EventBus::class.java)
    private val subscribers = CopyOnWriteArrayList<EventSubscriberData<out Any>>()

    fun <T : Any> publish(event: T, scope: CoroutineScope) {
        val eventSubscribers = subscribers
            .filter { it.eventType.isAssignableFrom(event::class.java) }

        val syncSubscribers = eventSubscribers.filter { it.isSync }
        invokeSyncEventHandler(syncSubscribers, event)

        val asyncSubscribers = eventSubscribers.filterNot { it.isSync }
        invokeAsyncEventHandler(asyncSubscribers, event, scope)
    }

    fun <T : Any> subscribe(subscriberData: EventSubscriberData<T>) {
        subscribers.add(subscriberData)
    }

    private fun <T : Any> invokeSyncEventHandler(eventHandlers: List<EventSubscriberData<out Any>>, event: T) {
        eventHandlers.forEach {
            @Suppress("UNCHECKED_CAST")
            invokeEventHandler((it as EventSubscriberData<T>), event)
        }
    }

    private fun <T : Any> invokeAsyncEventHandler(eventHandlers: List<EventSubscriberData<out Any>>, event: T, scope: CoroutineScope) {
        eventHandlers.forEach {
            scope.launch {
                @Suppress("UNCHECKED_CAST")
                invokeEventHandler((it as EventSubscriberData<T>), event)
            }
        }
    }

    private fun <T> invokeEventHandler(
        handler: EventSubscriberData<T>,
        event: T
    ) {
        try {
            handler.collector(event)
        } catch (e: Exception) {
            with(handler) {
                log.error(
                    "Got exception in event subscriber '${subscriberMethodName}(${eventType})' of ${subscriberClassType.simpleName} (${subscriberClassType})",
                    e.cause ?: e
                )
            }
        }
    }
}