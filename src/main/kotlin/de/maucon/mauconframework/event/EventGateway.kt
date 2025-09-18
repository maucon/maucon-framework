package de.maucon.mauconframework.event

import de.maucon.mauconframework.event.EventGateway.eventScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * A gateway for publishing events to the event bus.
 *
 * This object provides a method to publish events asynchronously using the [EventBus].
 */
object EventGateway {
    /**
     * A shared coroutine scope used for asynchronous event publishing.
     */
    private val eventScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

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
     * Publishes the given event to the event bus asynchronously using the shared coroutine scope.
     *
     * This method is non-blocking and intended for use in non-suspending contexts.
     * It launches a coroutine on the shared [eventScope].
     *
     * @param event The event instance to be published.
     */
    fun <T : Any> launchPublish(event: T) {
        eventScope.launch {
            EventBus.publish(event)
        }
    }

    /**
     * Publishes the given event to the event bus asynchronously using the provided [scope].
     *
     * This method is non-blocking and intended for use in non-suspending contexts.
     * It launches a coroutine within the given [scope] to publish the event.
     *
     * @param scope The [CoroutineScope] in which the coroutine will be launched.
     * @param event The event instance to be published.
     */
    fun <T : Any> launchPublish(scope: CoroutineScope, event: T) {
        scope.launch {
            EventBus.publish(event)
        }
    }
}