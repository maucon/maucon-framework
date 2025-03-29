package de.maucon.mauconframework.command.test003

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandGateway
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.di.annotation.Injectable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Command handler with priority")
object Test003 {
    @Test
    fun test002() {
        val components = assertDoesNotThrow { MauConFramework.start(Test003::class.java) }
        val componentClasses = components.map { it.javaClass }
        assertContains<Class<*>>(componentClasses, CommandHandler1::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler2::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler3::class.java)

        val cmd = CommandGateway.apply(TestCommand())

        assertEquals(3, cmd.count)
        assertEquals(listOf(3, 2, 1), cmd.callList)
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler(priority = 3)
    fun on(command: TestCommand) {
        println("CommandHandler1: got command: $command")
        command.count++
        command.callList.add(1)
    }
}

@Injectable
class CommandHandler2 {
    @CommandHandler(priority = 2)
    fun on(command: TestCommand) {
        println("CommandHandler2: got command: $command")
        command.count++
        command.callList.add(2)
    }
}

@Injectable
class CommandHandler3 {
    @CommandHandler(priority = 1)
    fun on(command: TestCommand) {
        println("CommandHandler3: got command: $command")
        command.count++
        command.callList.add(3)
    }
}

data class TestCommand(
    var count: Int = 0,
    val callList: MutableList<Int> = mutableListOf()
)