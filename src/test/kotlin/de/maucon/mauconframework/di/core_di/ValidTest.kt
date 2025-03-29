package de.maucon.mauconframework.di.core_di

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.core_di.test001.Test001
import de.maucon.mauconframework.di.core_di.test002.Test002
import de.maucon.mauconframework.di.core_di.test003.Test003
import de.maucon.mauconframework.di.core_di.test004.Test004
import de.maucon.mauconframework.di.core_di.test005.Test005
import de.maucon.mauconframework.di.core_di.test006.Test006
import de.maucon.mauconframework.di.core_di.test007.ConfigurationA
import de.maucon.mauconframework.di.core_di.test007.Test007
import de.maucon.mauconframework.di.core_di.test008.Test008
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains

@DisplayName("Valid framework configurations")
class ValidTest {
    @Test
    fun test001() {
        val components = assertDoesNotThrow { MauConFramework.start(Test001::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test001.Test001.ServiceA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test001.Test001.ServiceB::class.java)
    }

    @Test
    fun test002() {
        val components = assertDoesNotThrow { MauConFramework.start(Test002::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test002.ServiceA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test002.ServiceB::class.java)
    }

    @Test
    fun test003() {
        val components = assertDoesNotThrow { MauConFramework.start(Test003::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test003.ServiceA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test003.ServiceB::class.java)
    }

    @Test
    fun test004() {
        val components = assertDoesNotThrow { MauConFramework.start(Test004::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test004.ConfigurationA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test004.ServiceA::class.java)
    }

    @Test
    fun test005() {
        val components = assertDoesNotThrow { MauConFramework.start(Test005::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test005.SuperConfiguration::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test005.ServiceA::class.java)
    }

    @Test
    fun test006() {
        val components = assertDoesNotThrow { MauConFramework.start(Test006::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test006.ServiceA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test006.ServiceB::class.java)
    }

    @Test
    fun test007() {
        val components = assertDoesNotThrow { MauConFramework.start(Test007::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, ConfigurationA::class.java)
    }

    @Test
    fun test008() {
        val components = assertDoesNotThrow { MauConFramework.start(Test008::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test008.ObjectA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.di.core_di.test008.ClassB::class.java)
    }
}