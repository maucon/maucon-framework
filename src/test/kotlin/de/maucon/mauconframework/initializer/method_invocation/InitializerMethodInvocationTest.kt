package de.maucon.mauconframework.initializer.method_invocation

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.initializer.exception.InitializeMethodInvocationException
import de.maucon.mauconframework.initializer.method_invocation.test001.Test001
import de.maucon.mauconframework.initializer.method_invocation.test002.ConfigurationA
import de.maucon.mauconframework.initializer.method_invocation.test002.ServiceA
import de.maucon.mauconframework.initializer.method_invocation.test002.Test002
import de.maucon.mauconframework.initializer.method_invocation.test003.*
import de.maucon.mauconframework.initializer.method_invocation.test004.SuperConfiguration
import de.maucon.mauconframework.initializer.method_invocation.test004.Test004
import de.maucon.mauconframework.initializer.method_invocation.test005.Test005
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("Initializer method invocation exceptions")
class InitializerMethodInvocationTest {
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

    @Test
    fun test004() {
        val components = assertDoesNotThrow { MauConFramework.start(Test004::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, SuperConfiguration::class.java)
        assertTrue { Test004.subConfigInvocation }
    }

    @Test
    fun test005() {
        val components = assertDoesNotThrow { MauConFramework.start(Test005::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.initializer.method_invocation.test005.SuperConfiguration::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.initializer.method_invocation.test005.ServiceA::class.java)
        assertTrue { Test005.subConfigInvocation }
    }
}