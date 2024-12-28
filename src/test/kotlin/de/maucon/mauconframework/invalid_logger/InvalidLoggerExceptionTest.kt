package de.maucon.mauconframework.invalid_logger

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.exception.InvalidLoggerException
import de.maucon.mauconframework.di.exception.ResolveComponentException
import de.maucon.mauconframework.invalid_logger.test001.Test001
import de.maucon.mauconframework.invalid_logger.test002.Test002
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("Invalid logger exceptions")
class InvalidLoggerExceptionTest {
    @Test
    fun test001() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test001::class.java) }
        exception.printStackTrace()
        assertEquals(InvalidLoggerException::class, exception.cause!!::class)
    }

    @Test
    fun test002() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test002::class.java) }
        exception.printStackTrace()
        assertEquals(InvalidLoggerException::class, exception.cause!!::class)
    }
}