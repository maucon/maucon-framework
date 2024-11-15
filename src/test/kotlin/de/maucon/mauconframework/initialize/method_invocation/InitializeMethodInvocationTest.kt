package de.maucon.mauconframework.initialize.method_invocation

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.exception.InitializeMethodInvocationException
import de.maucon.mauconframework.initialize.method_invocation.test001.Test001
import de.maucon.mauconframework.initialize.method_invocation.test002.ConfigurationA
import de.maucon.mauconframework.initialize.method_invocation.test002.ServiceA
import de.maucon.mauconframework.initialize.method_invocation.test002.Test002
import de.maucon.mauconframework.initialize.method_invocation.test003.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertContains
import kotlin.test.assertEquals

@DisplayName("Initialize method invocation exceptions")
class InitializeMethodInvocationTest {
    @Test
    fun test001() {
        val exception = assertThrows<InitializeMethodInvocationException> { MauConFramework.start(Test001::class.java) }
        exception.printStackTrace()
    }

    @Test
    fun test002() {
        val components = assertDoesNotThrow { MauConFramework.start(Test002::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, ConfigurationA::class.java)
        assertContains<Class<*>>(componentClasses, ServiceA::class.java)
    }

    @Test
    fun test003() {
        val components = assertDoesNotThrow { MauConFramework.start(Test003::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, Service1::class.java)
        assertContains<Class<*>>(componentClasses, Service2::class.java)
        assertContains<Class<*>>(componentClasses, Service3::class.java)
        assertContains<Class<*>>(componentClasses, Service4::class.java)
        assertContains<Class<*>>(componentClasses, Service5::class.java)

        assertEquals(listOf(5, 4, 3, 2, 1), Test003.invocationOrder)
    }
}