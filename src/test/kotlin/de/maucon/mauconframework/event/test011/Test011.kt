package de.maucon.mauconframework.event.test011

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
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

private val log = LoggerFactory.getLogger(Test011::class.java)

@DisplayName("Sync event subscriber ordering")
object Test011 {
    var callCounter = AtomicInteger(0)
    val syncThreadNames = mutableListOf<String>()
    val asyncThreadNames = mutableListOf<String>()

    @Test
    fun testSyncExecutedFirst() = runBlocking {
        val components = assertDoesNotThrow { MauConFramework.start(Test011::class.java) }
        val componentClasses = components.map { it.javaClass }
        assertContains<Class<*>>(componentClasses, EventSub1::class.java)

        repeat(2) { i ->
            EventGateway.publish(TestEvent("launchPublish Test001 $i"))
            delay(10)
        }

        assertEquals(4, callCounter.get())

        syncThreadNames.forEach { thread ->
            assertEquals(Thread.currentThread().name, thread, "Sync handler should run on main thread")
        }

        asyncThreadNames.forEach { thread ->
            assertNotEquals(Thread.currentThread().name, thread, "Async handler should not run on main thread")
        }
        assertTrue(callOrder.map { it.second }.all { it == "sync" || it == "async" })
    }

    val callOrder = mutableListOf<Pair<Long, String>>() // (timestamp, "sync"/"async")
}

@Injectable
class EventSub1 {
    @EventSubscriber(sync = true)
    fun on(event: TestEvent) {
        val threadName = Thread.currentThread().name
        log.info("SYNC EventSub1: got event: $event $threadName")
        Test011.callCounter.incrementAndGet()
        Test011.syncThreadNames.add(threadName)
        Test011.callOrder.add(System.currentTimeMillis() to "sync")
    }

    @EventSubscriber
    fun on2(event: TestEvent) {
        val threadName = Thread.currentThread().name
        log.info("ASYNC EventSub1: got event: $event $threadName")
        Test011.callCounter.incrementAndGet()
        Test011.asyncThreadNames.add(threadName)
        Test011.callOrder.add(System.currentTimeMillis() to "async")
    }
}

data class TestEvent(val name: String)