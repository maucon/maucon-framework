package de.maucon.mauconframework.di.exception

class ComponentInstantiationException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)