package de.maucon.mauconframework.event.service

import de.maucon.mauconframework.di.data.ComponentDefinition
import de.maucon.mauconframework.event.EventBus
import de.maucon.mauconframework.event.EventSubscriber
import de.maucon.mauconframework.event.EventSubscriberData
import de.maucon.mauconframework.event.exception.InvalidEventSubscriberParameterSizeException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.lang.reflect.Method

internal object EventService {
    private val log = LoggerFactory.getLogger(EventService::class.java)

    internal fun registerEvents(
        components: Map<ComponentDefinition, Any>,
        scope: CoroutineScope
    ) {
        val subscriberInfos = gatherEventSubscriberInfos(components)
        log.debug("Registering ${subscriberInfos.size} event subscriber")

        subscriberInfos.forEach { info ->
            scope.launch {
                log.debug("Register subscriber for '{}' in '{}' of {}", info.eventType.simpleName, info.methodName, info.componentDefinition)

                info.registerSubscriber()
            }
        }
    }

    private fun gatherEventSubscriberInfos(components: Map<ComponentDefinition, Any>): List<EventSubscriberInfo> =
        components.flatMap { (componentDefinition, instance) ->
            componentDefinition.type.methods
                .filter { it.isAnnotationPresent(EventSubscriber::class.java) }
                .map { mapToEventSubscriberInfo(it, componentDefinition, instance) }
        }

    private fun mapToEventSubscriberInfo(
        method: Method,
        componentDefinition: ComponentDefinition,
        instance: Any
    ): EventSubscriberInfo {
        val parameters = method.parameterTypes
        if (parameters.size != 1) {
            throw InvalidEventSubscriberParameterSizeException(
                "Event subscriber '${method}' of ${componentDefinition.type.simpleName} (${componentDefinition.type}) must have exactly one parameter, but has ${parameters.size}"
            )
        }

        @Suppress("UNCHECKED_CAST")
        val eventType = parameters.first() as Class<Any>

        method.isAccessible = true

        val subscriberData = EventSubscriberData(
            componentDefinition.type,
            method.name,
            eventType
        ) { method.invoke(instance, it) }
        val registerSubscriber = suspend { EventBus.subscribe(subscriberData) }

        return EventSubscriberInfo(componentDefinition, method.name, eventType, registerSubscriber)
    }
}