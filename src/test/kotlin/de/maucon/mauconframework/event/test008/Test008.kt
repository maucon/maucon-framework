package de.maucon.mauconframework.event.test008

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventGateway
import de.maucon.mauconframework.event.EventSubscriber
import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val log = LoggerFactory.getLogger(Test008::class.java)

@DisplayName("Exception in subscriber with async launch publish")
object Test008 {
    var callCounter = AtomicInteger(0)

    @Test
    fun test001() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.start(Test008::class.java) }
            val componentClasses = components.map { it.javaClass }
            assertContains<Class<*>>(componentClasses, EventSub1::class.java)
            assertContains<Class<*>>(componentClasses, EventSub2::class.java)

        }
        job.join()

        for (i in 0..1) {
            delay(10)
            EventGateway.publish(TestEvent("Test001 $i"))
        }
        delay(10)

        assertEquals(2, callCounter.get())
    }
}

@Injectable
class EventSub1 {
    @EventSubscriber
    fun on(event: TestEvent) {
        throw RuntimeException("EventSub1 exception")
        log.info("EventSub1: got event: $event")
        Test008.callCounter.incrementAndGet()
    }
}

@Injectable
class EventSub2 {
    @EventSubscriber
    fun on(event: TestEvent) {
        log.info("EventSub2: got event: $event")
        Test008.callCounter.incrementAndGet()
    }
}

data class TestEvent(val name: String)