package de.maucon.mauconframework.command.test002

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandGateway
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.di.annotation.Injectable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Multiple command handler")
object Test002 {
    @Test
    fun test002() {
        val components = assertDoesNotThrow { MauConFramework.start(Test002::class.java) }
        val componentClasses = components.map { it.javaClass }
        assertContains<Class<*>>(componentClasses, CommandHandler1::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler2::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler3::class.java)

        val cmd = CommandGateway.apply(TestCommand())

        assertEquals(3, cmd.count)
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler
    fun on(command: TestCommand) {
        println("CommandHandler1: got command: $command")
        command.count++
    }
}

@Injectable
class CommandHandler2 {
    @CommandHandler
    fun on(command: TestCommand) {
        println("CommandHandler2: got command: $command")
        command.count++
    }
}

@Injectable
class CommandHandler3 {
    @CommandHandler
    fun on(command: TestCommand) {
        println("CommandHandler3: got command: $command")
        command.count++
    }
}

data class TestCommand(
    var count: Int = 0
)