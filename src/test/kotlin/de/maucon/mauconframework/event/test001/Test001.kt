package de.maucon.mauconframework.event.test001

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventGateway
import de.maucon.mauconframework.event.EventSubscriber
import de.maucon.mauconframework.event.test007.Test007
import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.atomics.AtomicInt
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val log = LoggerFactory.getLogger(Test001::class.java)

@DisplayName("Single event subscriber")
object Test001 {
    var callCounter = AtomicInteger(0)

    @Test
    fun test001() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.start(Test001::class.java) }
            val componentClasses = components.map { it.javaClass }
            assertContains<Class<*>>(componentClasses, EventSub::class.java)

            for (i in 0..2) {
                delay(10)
                EventGateway.publish(TestEvent("Test001 $i"))
            }
            cancel()
        }
        job.join()

        assertEquals(3, callCounter.get())
    }
}

@Injectable
class EventSub {
    @EventSubscriber
    fun on(event: TestEvent) {
        log.info("got event: $event")
        Test001.callCounter.incrementAndGet()
    }
}

data class TestEvent(val name: String)