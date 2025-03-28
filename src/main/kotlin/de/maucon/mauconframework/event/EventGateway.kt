package de.maucon.mauconframework.event

object EventGateway {
    suspend fun <T : Any> apply(event: T) {
        EventBus.publish(event)
    }
}