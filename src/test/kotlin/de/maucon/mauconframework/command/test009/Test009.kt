package de.maucon.mauconframework.command.test009

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.command.exception.InvalidCommandHandlerParameterSizeException
import de.maucon.mauconframework.di.annotation.Injectable
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Command handler no parameter")
object Test009 {
    @Test
    fun test() = runBlocking {
        val exception = assertThrows<InvalidCommandHandlerParameterSizeException> { MauConFramework.start(Test009::class.java, this) }
        exception.printStackTrace()
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler
    fun on() {
    }
}
