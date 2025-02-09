package de.maucon.mauconframework.event

object EventGateway {
    suspend fun <T : Event> apply(event: T) {
        EventBus.publish(event)
    }
}