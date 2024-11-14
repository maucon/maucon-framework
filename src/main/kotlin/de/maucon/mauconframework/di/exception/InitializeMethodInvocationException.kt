package de.maucon.mauconframework.di.exception

class InitializeMethodInvocationException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)