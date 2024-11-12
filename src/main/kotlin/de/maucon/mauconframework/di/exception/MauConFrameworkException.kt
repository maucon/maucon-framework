package de.maucon.mauconframework.di.exception

open class MauConFrameworkException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)