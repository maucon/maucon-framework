package de.maucon.mauconframework.command.test006

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandGateway
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.di.annotation.Injectable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Different commands")
object Test006 {
    @Test
    fun test002() {
        val components = assertDoesNotThrow { MauConFramework.start(Test006::class.java) }
        val componentClasses = components.map { it.javaClass }
        assertContains<Class<*>>(componentClasses, CommandHandler1::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler2::class.java)

        val genericCmd = CommandGateway.apply(GenericCommand())
        val upperCmd = CommandGateway.apply(UpperCommand())

        assertEquals(2, upperCmd.count)
        assertEquals(1, genericCmd.count)
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler(priority = 1)
    fun on(command: UpperCommand) {
        println("CommandHandler1: got command: $command")
        command.count++
    }
}

@Injectable
class CommandHandler2 {
    @CommandHandler(priority = 2)
    fun on(command: GenericCommand) {
        println("CommandHandler2: got command: $command")
        command.count++
    }
}

class UpperCommand : GenericCommand()

open class GenericCommand(
    var count: Int = 0,
)