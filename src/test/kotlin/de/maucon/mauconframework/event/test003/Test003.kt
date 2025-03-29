package de.maucon.mauconframework.event.test003

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

@DisplayName("Polymorphic events")
object Test003 {
    var genericCallCounter = 0
    var upperCallCounter = 0

    @Test
    fun test003() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.start(Test003::class.java, this) }
            val componentClasses = components.map { it.javaClass }
            assertContains<Class<*>>(componentClasses, EventSub1::class.java)
            assertContains<Class<*>>(componentClasses, EventSub2::class.java)

            for (i in 0..2) {
                delay(10)
                EventGateway.apply(UpperEvent("test$i"))
                EventGateway.apply(GenericEvent())
            }
            cancel()
        }
        job.join()

        assertEquals(6, genericCallCounter)
        assertEquals(3, upperCallCounter)
    }
}

@Injectable
class EventSub1 {
    @EventSubscriber
    fun on(event: UpperEvent) {
        println("EventSub1: got event: $event")
        Test003.upperCallCounter++
    }
}

@Injectable
class EventSub2 {
    @EventSubscriber
    fun on(event: GenericEvent) {
        println("EventSub2: got event: $event")
        Test003.genericCallCounter++
    }
}

open class GenericEvent
data class UpperEvent(val name: String) : GenericEvent()
