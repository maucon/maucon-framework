package de.maucon.mauconframework.initializer.exception

import de.maucon.mauconframework.di.exception.MauConFrameworkException

class InitializeMethodUnresolvedDependencyException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)