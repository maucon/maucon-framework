package de.maucon.mauconframework.di.exception

class ResolveComponentException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)