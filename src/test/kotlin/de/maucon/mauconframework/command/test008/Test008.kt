package de.maucon.mauconframework.command.test008

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.command.exception.InvalidCommandHandlerParameterSizeException
import de.maucon.mauconframework.di.annotation.Injectable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Command handler too many parameter")
object Test008 {
    @Test
    fun test() {
        val exception = assertThrows<InvalidCommandHandlerParameterSizeException> { MauConFramework.start(Test008::class.java) }
        exception.printStackTrace()
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler
    fun on(a: Int, b: String) {
    }
}
