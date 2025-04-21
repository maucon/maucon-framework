package de.maucon.mauconframework.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A gateway for publishing events to the event bus.
 *
 * This object provides a method to publish events asynchronously using the `EventBus`.
 */
object EventGateway {
    /**
     * Publishes the given event to the event bus.
     *
     * This function suspends execution while the event is being processed.
     *
     * @param event The event instance to be published.
     */
    suspend fun <T : Any> publish(event: T) {
        EventBus.publish(event)
    }

    /**
     * Publishes the given event to the event bus using the provided coroutine scope.
     *
     * If no scope is provided, a new one will be created using [Dispatchers.Default].
     * This is useful when publishing events from non-coroutine contexts.
     *
     * @param event The event instance to be published.
     */
    fun <T : Any> launchPublish(
        event: T,
        scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) {
        scope.launch {
            EventBus.publish(event)
        }
    }
}