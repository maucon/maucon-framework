package de.maucon.mauconframework.command.test005

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.command.CommandGateway
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.command.cancellable.CancellableCommand
import de.maucon.mauconframework.di.annotation.Injectable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Cancelled command with ignoreCancelled")
object Test005 {
    @Test
    fun test005() {
        val components = assertDoesNotThrow { MauConFramework.start(Test005::class.java) }
        val componentClasses = components.map { it.javaClass }
        assertContains<Class<*>>(componentClasses, CommandHandler1::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler2::class.java)
        assertContains<Class<*>>(componentClasses, CommandHandler3::class.java)

        val cmd = CommandGateway.apply(TestCommand())

        assertEquals(2, cmd.count)
        assertEquals(listOf(1, 2), cmd.callList)
    }
}

@Injectable
class CommandHandler1 {
    @CommandHandler(priority = 1)
    fun on(command: TestCommand) {
        println("CommandHandler1: got command: $command")
        command.count++
        command.callList.add(1)
        command.cancel()
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
    @CommandHandler(priority = 3, ignoreCancelled = true)
    fun on(command: TestCommand) {
        println("CommandHandler3: got command: $command")
        command.count++
        command.callList.add(3)
    }
}

data class TestCommand(
    var count: Int = 0,
    val callList: MutableList<Int> = mutableListOf()
) : CancellableCommand()