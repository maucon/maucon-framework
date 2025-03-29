package de.maucon.mauconframework.command.test008

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.command.exception.InvalidCommandHandlerParameterSizeException
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventSubscriber
import de.maucon.mauconframework.event.exception.InvalidEventSubscriberParameterSizeException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Command handler too many parameter")
object Test008 {
    @Test
    fun test() = runBlocking {
        val exception = assertThrows<InvalidCommandHandlerParameterSizeException> { MauConFramework.startAsync(Test008::class.java, this) }
        exception.printStackTrace()
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler
    fun on(a: Int, b: String) {
    }
}
