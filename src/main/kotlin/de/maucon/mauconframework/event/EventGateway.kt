package de.maucon.mauconframework.event

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
    suspend fun <T : Any> apply(event: T) {
        EventBus.publish(event)
    }
}