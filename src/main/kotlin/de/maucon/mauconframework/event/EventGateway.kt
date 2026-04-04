package de.maucon.mauconframework.event

import de.maucon.mauconframework.event.EventGateway.defaultScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * A gateway for publishing events to the [EventBus].
 *
 * This object provides a simplified entry point for dispatching events without
 * directly interacting with the underlying bus implementation.
 */
object EventGateway {
    /**
     * A shared default [CoroutineScope] used to launch asynchronous event subscribers.
     *
     * Backed by a [SupervisorJob] and [Dispatchers.Default], this scope ensures that
     * failures in one subscriber do not cancel others.
     */
    private val defaultScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    /**
     * Publishes the given [event] to the [EventBus].
     *
     * All matching subscribers are invoked:
     * - Synchronous subscribers are executed immediately on the calling thread.
     * - Asynchronous subscribers are launched in [defaultScope].
     *
     * @param event The event instance to publish.
     */
    fun <T : Any> publish(event: T) {
        EventBus.publish(event, defaultScope)
    }

    /**
     * Publishes the given [event] to the [EventBus].
     *
     * All matching subscribers are invoked:
     * - Synchronous subscribers are executed immediately on the calling thread.
     * - Asynchronous subscribers are launched in the provided [scope].
     *
     * @param event The event instance to publish.
     * @param scope The [CoroutineScope] used to launch asynchronous subscriber handlers.
     *              This allows callers to control lifecycle and cancellation behavior.
     */
    fun <T : Any> publish(
        event: T,
        scope: CoroutineScope,
    ) {
        EventBus.publish(event, scope)
    }
}