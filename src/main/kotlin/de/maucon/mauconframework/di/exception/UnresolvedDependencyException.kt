package de.maucon.mauconframework.di.exception

class UnresolvedDependencyException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)