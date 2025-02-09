package de.maucon.mauconframework.event

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.annotation.Injectable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@DisplayName("Lole")
class Test {
    @Test
    fun test001() {
        val job = CoroutineScope(Dispatchers.Default).launch {
            val components = assertDoesNotThrow { MauConFramework.startAsync(Test001::class.java, this) }
            println(components)

            launch {
                delay(1000)
                for (i in 0..2) EventGateway.apply(TestEvent("test$i"))
            }
        }

        Thread.sleep(1500)
        job.cancel()
        println("finished")
    }
}

@Injectable
class Test001 {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("test001 $event")
    }
}

@Injectable
object Test002 {
    @EventSubscriber
    fun on(event: TestEvent) {
        println("test002 $event")
    }
}

data class TestEvent(
    val name: String
) : Event()