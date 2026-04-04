package de.maucon.mauconframework.command.exception

import de.maucon.mauconframework.di.exception.MauConFrameworkException

class CommandExecutionException(
    message: String,
    cause: Throwable? = null
) : MauConFrameworkException(message, cause)