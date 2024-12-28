package de.maucon.mauconframework.annotation

import de.maucon.mauconframework.di.exception.InvalidLoggerException

/**
 * Annotation to specify that a parameter should be injected with a logger.
 *
 * This annotation is intended to be used on constructor or method parameters of type [org.slf4j.Logger].
 * The logger will be automatically resolved based on the class where it is injected.
 *
 * Example usage:
 * ```
 * @Injectable
 * class MyService(
 *     @Logging private val log: Logger
 * ) {
 *     fun doSomething() {
 *         log.info("Doing something...")
 *     }
 * }
 * ```
 *
 * **Note:** Applying this annotation to parameters that are not of type [org.slf4j.Logger]
 * will result in an [InvalidLoggerException].
 *
 * @throws InvalidLoggerException if applied to a parameter that is not of type [org.slf4j.Logger].
 * @see Injectable
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Logging