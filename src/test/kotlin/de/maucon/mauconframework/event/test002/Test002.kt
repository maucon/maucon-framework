package de.maucon.mauconframework.event.test002

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

@DisplayName("Multiple event subscriber")
object Test002 {
    var callCounter1 = 0
    var callCounter2 = 0
    var callCounter3 = 0

    @Test
    fun test002() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.start(Test002::class.java, this) }
            val componentClasses = components.map { it.javaClass }
            assertContains<Class<*>>(componentClasses, EventSub1::class.java)
            assertContains<Class<*>>(componentClasses, EventSub2::class.java)

            for (i in 0..2) {
                delay(10)
                EventGateway.publish(TestEvent("test$i"))
            }
            cancel()
        }
        job.join()

        assertEquals(3, callCounter1)
        assertEquals(3, callCounter2)
        assertEquals(3, callCounter3)
    }
}

@Injectable
class EventSub1 {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("EventSub1: got event: $event")
        Test002.callCounter1++
    }
}

@Injectable
class EventSub2 {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("EventSub2: got event: $event")
        Test002.callCounter2++
    }
}

@Injectable
class EventSub3 {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("EventSub3: got event: $event")
        Test002.callCounter3++
    }
}

data class TestEvent(val name: String)