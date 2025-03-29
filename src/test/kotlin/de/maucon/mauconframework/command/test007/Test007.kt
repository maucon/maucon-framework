package de.maucon.mauconframework.command.test007

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandGateway
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.di.annotation.Injectable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Command handler throwing exception")
object Test007 {
    @Test
    fun test007() {
        val components = assertDoesNotThrow { MauConFramework.start(Test007::class.java) }
        val componentClasses = components.map { it.javaClass }
        assertContains<Class<*>>(componentClasses, CommandHandler1::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler2::class.java)

        val genericCmd = CommandGateway.apply(TestCommand())

        assertEquals(2, genericCmd.count)
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler(priority = 1)
    fun on(command: TestCommand) {
        println("CommandHandler1: got command: $command")
        command.count++

        throw RuntimeException("CommandHandler1 throwing exception")
    }
}

@Injectable
class CommandHandler2 {
    @CommandHandler(priority = 2)
    fun on(command: TestCommand) {
        println("CommandHandler2: got command: $command")
        command.count++
    }
}

open class TestCommand(
    var count: Int = 0,
)