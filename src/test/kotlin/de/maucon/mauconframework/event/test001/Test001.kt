package de.maucon.mauconframework.event.test001

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventGateway
import de.maucon.mauconframework.event.EventSubscriber
import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Single event subscriber")
object Test001 {
    var callCounter = 0

    @Test
    fun test001() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.startAsync(Test001::class.java, this) }
            val componentClasses = components.map { it.javaClass }
            assertContains<Class<*>>(componentClasses, EventSub::class.java)

            for (i in 0..2) {
                delay(10)
                EventGateway.apply(TestEvent("test$i"))
            }
            cancel()
        }
        job.join()

        assertEquals(3, callCounter)
    }
}

@Injectable
class EventSub {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("got event: $event")
        Test001.callCounter++
    }
}

data class TestEvent(val name: String)