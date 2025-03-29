package de.maucon.mauconframework.command.test001

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandGateway
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.di.annotation.Injectable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Single command handler")
object Test001 {
    @Test
    fun test001() {
        val components = assertDoesNotThrow { MauConFramework.start(Test001::class.java) }
        val componentClasses = components.map { it.javaClass }
        assertContains<Class<*>>(componentClasses, CommandHandler1::class.java)

        val cmd = CommandGateway.apply(TestCommand("test"))

        assertEquals("CommandHandler1", cmd.name)
        assertEquals(1, cmd.count)
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler
    fun on(command: TestCommand) {
        println("got command: $command")

        command.count++
        command.name = "CommandHandler1"
    }
}

data class TestCommand(
    var name: String,
    var count: Int = 0
)