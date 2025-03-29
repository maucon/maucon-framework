package de.maucon.mauconframework.event.exception

import de.maucon.mauconframework.di.exception.MauConFrameworkException

class InvalidEventSubscriberParameterSizeException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)