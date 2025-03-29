package de.maucon.mauconframework.initializer.exception

import de.maucon.mauconframework.di.exception.MauConFrameworkException

class InitializeMethodInvocationException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)