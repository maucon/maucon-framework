package de.maucon.mauconframework.annotation

/**
 * Marks a function to be automatically called after dependency injection is complete.
 *
 * This annotation should be used on functions within classes annotated with [Injectable] or [Configuration].
 * When the dependency injection framework finishes injecting dependencies into the components,
 * it will look for methods annotated with [Initialize] and invoke them automatically.
 *
 * The function annotated with `@Initialize` can also define parameters for other dependencies
 * provided by the dependency injection framework.
 *
 * ### Example Usage
 * ```kotlin
 * @Injectable // or @Configuration
 * class MyService(
 *     private val dependency: SomeDependency
 * ) {
 *     // Function will be called automatically after injection is complete
 *     @Initialize
 *     fun setup(otherDependency: OtherDependency) {...}
 * }
 * ```
 *
 * @see Injectable
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Initialize