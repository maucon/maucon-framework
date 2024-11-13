package de.maucon.mauconframework.annotation

/**
 * Marks a class as a configuration source for defining injectable components.
 *
 * Classes annotated with [Configuration] can contain methods annotated with [Injectable] to
 * define and provide dependencies within the dependency injection framework.
 *
 * Using this annotation allows the framework to identify classes that should be processed
 * for dependency injection setup, such as defining singleton instances or factory methods.
 *
 * Example usage:
 * ```
 * @Configuration
 * class AppConfig {
 *     @Injectable
 *     fun provideService(): Service { ... }
 * }
 * ```
 *
 * @see Injectable
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Configuration