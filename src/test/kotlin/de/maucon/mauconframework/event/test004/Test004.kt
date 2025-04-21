package de.maucon.mauconframework.event.test004

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

@DisplayName("Event subscriber throwing exception")
object Test004 {
    var callCounter = 0

    @Test
    fun test004() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.start(Test004::class.java, this) }
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

        assertEquals(6, callCounter)
    }
}

@Injectable
class EventSub1 {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("EventSub1: got event: $event")
        Test004.callCounter++

        throw RuntimeException("EventSub throws exception")
    }
}

@Injectable
class EventSub2 {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("EventSub2: got event: $event")
        Test004.callCounter++
    }
}

data class TestEvent(val name: String)
