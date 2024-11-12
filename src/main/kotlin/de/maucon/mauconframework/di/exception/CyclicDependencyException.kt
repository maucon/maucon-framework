package de.maucon.mauconframework.di.exception

class CyclicDependencyException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)