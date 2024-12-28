package de.maucon.mauconframework.di.exception

import de.maucon.mauconframework.annotation.Logging

/**
 * Exception thrown when the [Logging] annotation is used on an invalid parameter type.
 *
 * This exception indicates a misconfiguration or misuse of the [Logging] annotation,
 * which is only allowed on parameters of type [org.slf4j.Logger].
 *
 * @param message Detailed error message explaining the misuse.
 */
class InvalidLoggerException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)