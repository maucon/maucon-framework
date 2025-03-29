package de.maucon.mauconframework.command.exception

import de.maucon.mauconframework.di.exception.MauConFrameworkException

class InvalidCommandHandlerParameterSizeException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)