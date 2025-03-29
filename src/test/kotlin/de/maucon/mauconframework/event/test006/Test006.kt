package de.maucon.mauconframework.event.test006

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventSubscriber
import de.maucon.mauconframework.event.exception.InvalidEventSubscriberParameterSizeException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Event subscriber no parameter")
object Test006 {
    @Test
    fun test() = runBlocking {
        val exception = assertThrows<InvalidEventSubscriberParameterSizeException> { MauConFramework.start(Test006::class.java, this) }
        exception.printStackTrace()
    }
}

@Injectable
class EventSub1 {
    @EventSubscriber
    fun on() {
    }
}
