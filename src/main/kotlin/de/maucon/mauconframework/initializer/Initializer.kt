package de.maucon.mauconframework.initializer

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.di.annotation.Injectable

/**
 * Marks a function to be automatically called after dependency injection is complete.
 *
 * This annotation should be used on functions within classes annotated with [Injectable] or [Configuration].
 * When the dependency injection framework finishes injecting dependencies into the components,
 * it will look for methods annotated with [Initializer] and invoke them automatically.
 *
 * The function annotated with `@Initializer` can also define parameters for other dependencies
 * provided by the dependency injection framework.
 *
 * ### Priority
 * The `priority` parameter allows you to control the order in which `@Initializer` methods
 * are invoked within the dependency injection framework. Methods with a lower priority value
 * are called before methods with higher priority values. By default, the priority is set to `Int.MAX_VALUE`.
 *
 * This can be useful when specific initialization methods must run before others to ensure
 * proper setup of dependencies or configurations.
 *
 * ### Example Usage
 * ```kotlin
 * @Injectable // or @Configuration
 * class MyService(
 *     private val dependency: SomeDependency
 * ) {
 *     // Function will be called automatically after injection is complete
 *     @Initializer(priority = 1)
 *     fun setup(otherDependency: OtherDependency) {...}
 * ```
 *
 * @property priority The priority value determining the order of execution. Lower values indicate higher precedence.
 * @see Injectable
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Initializer(
    val priority: Int = Int.MAX_VALUE
)
