package de.maucon.mauconframework.event

/**
 * Annotation for marking methods as event subscribers.
 *
 * Methods annotated with `@EventSubscriber` will be automatically registered
 * to receive events of the specified type when published via `EventGateway`.
 *
 * @see EventGateway
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventSubscriber