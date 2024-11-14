package de.maucon.mauconframework.di.exception

class InitializeMethodUnresolvedDependencyException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)