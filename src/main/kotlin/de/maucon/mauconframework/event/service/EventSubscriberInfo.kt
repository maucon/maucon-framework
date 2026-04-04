package de.maucon.mauconframework.event.service

import de.maucon.mauconframework.di.data.ComponentDefinition

internal data class EventSubscriberInfo(
    val componentDefinition: ComponentDefinition,
    val methodName: String,
    val eventType: Class<*>,
    val registerSubscriber: () -> Unit,
)