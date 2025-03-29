package de.maucon.mauconframework.event.test005

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventSubscriber
import de.maucon.mauconframework.event.exception.InvalidEventSubscriberParameterSizeException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Event subscriber too many parameter")
object Test005 {
    @Test
    fun test() = runBlocking {
        val exception = assertThrows<InvalidEventSubscriberParameterSizeException> { MauConFramework.startAsync(Test005::class.java, this) }
        exception.printStackTrace()
    }
}

@Injectable
class EventSub1 {
    @EventSubscriber
    fun on(a: Int, b: String) {
    }
}
