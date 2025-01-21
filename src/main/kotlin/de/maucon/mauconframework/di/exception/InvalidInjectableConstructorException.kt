package de.maucon.mauconframework.di.exception

class InvalidInjectableConstructorException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)