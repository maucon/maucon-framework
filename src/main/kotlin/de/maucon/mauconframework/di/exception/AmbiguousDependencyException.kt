package de.maucon.mauconframework.di.exception

class AmbiguousDependencyException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)