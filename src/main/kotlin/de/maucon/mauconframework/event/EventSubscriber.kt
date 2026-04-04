package de.maucon.mauconframework.event

/**
 * Annotation for marking methods as event subscribers.
 *
 * Methods annotated with `@EventSubscriber` are automatically registered
 * to receive events of a compatible type when published via [EventGateway].
 * The event type is inferred from the method’s single parameter.
 *
 * ### Execution model
 * The behavior of the subscriber depends on the [sync] flag:
 *
 * - `sync = true`
 *   The subscriber is invoked **synchronously** on the calling thread that
 *   publishes the event. Execution is blocking, and handlers are invoked
 *   sequentially.
 *
 * - `sync = false` (default)
 *   The subscriber is invoked **asynchronously** in a coroutine launched
 *   in the publishing scope. Execution is non-blocking, and multiple
 *   subscribers may run concurrently.
 *
 * Exceptions thrown by a subscriber are caught and logged internally and
 * do not propagate back to the publisher.
 *
 * ### Requirements
 * - The annotated method must accept exactly **one parameter** (the event).
 * - The parameter type defines which events the method will receive.
 *
 * ### Example
 * ```kotlin
 * class UserEventHandler {
 *
 *     @EventSubscriber(sync = true)
 *     fun onUserCreated(event: UserCreatedEvent) {
 *         println("Synchronously handling user: ${event.userId}")
 *     }
 *
 *     @EventSubscriber
 *     fun onUserCreatedAsync(event: UserCreatedEvent) {
 *         println("Asynchronously handling user: ${event.userId}")
 *     }
 * }
 * ```
 *
 * @param sync If `true`, the subscriber is executed synchronously on the
 * calling thread. If `false`, it is executed asynchronously in a coroutine.
 *
 * @see EventGateway
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventSubscriber(
    val sync: Boolean = false
)