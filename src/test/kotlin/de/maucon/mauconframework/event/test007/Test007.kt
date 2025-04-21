package de.maucon.mauconframework.event.test007

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventGateway
import de.maucon.mauconframework.event.EventSubscriber
import kotlinx.coroutines.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.slf4j.LoggerFactory
import kotlin.test.assertContains
import kotlin.test.assertEquals

private val log = LoggerFactory.getLogger(Test007::class.java)

@DisplayName("Using async launch publish")
object Test007 {
    var callCounter = 0

    @Test
    fun test001() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.start(Test007::class.java) }
            val componentClasses = components.map { it.javaClass }
            assertContains<Class<*>>(componentClasses, EventSub::class.java)

        }
        job.join()

        for (i in 0..2) {
            delay(10)
            EventGateway.launchPublish(TestEvent("Test001 $i"))
        }
        delay(10)

        assertEquals(3, callCounter)
    }
}

@Injectable
class EventSub {
    @EventSubscriber
    fun on(event: TestEvent) {
        log.info("got event: $event")
        Test007.callCounter++
    }
}

data class TestEvent(val name: String)