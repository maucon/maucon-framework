package de.maucon.mauconframework.di.annotation

/**
 * Marks a class or method as an injectable component in the dependency injection framework.
 *
 * This annotation can be applied to classes or methods that are intended to be injectable.
 * When applied to a method, the method must be within a class annotated with [Configuration].
 *
 * Example usage:
 * ```
 * @Injectable("customName")
 * class CustomService(val anotherService: Service) { ... }
 *
 * @Configuration
 * class AppConfig {
 *     @Injectable
 *     fun anotherService(): Service { ... }
 * }
 * ```
 *
 * @property name An optional custom name for the component, which allows named dependency resolution.
 * If left blank, the class or method name will be used by default. Dependencies can use the [Qualifier]
 * annotation to specify a matching name for resolution.
 *
 * @see Configuration
 * @see Qualifier
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Injectable(
    val name: String = ""
)