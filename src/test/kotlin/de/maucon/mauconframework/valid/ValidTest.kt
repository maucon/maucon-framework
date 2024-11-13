package de.maucon.mauconframework.valid

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.valid.test001.Test001
import de.maucon.mauconframework.valid.test002.Test002
import de.maucon.mauconframework.valid.test003.Test003
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertContains
import kotlin.test.assertTrue

@DisplayName("Valid framework configurations")
class ValidTest {
    @Test
    fun test001() {
        val components = assertDoesNotThrow { MauConFramework.start(Test001::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, Test001.ServiceA::class.java)
        assertContains<Class<*>>(componentClasses, Test001.ServiceB::class.java)

        assertTrue(false)
    }

    @Test
    fun test002() {
        val components = assertDoesNotThrow { MauConFramework.start(Test002::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.valid.test002.ServiceA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.valid.test002.ServiceB::class.java)
    }

    @Test
    fun test003() {
        val components = assertDoesNotThrow { MauConFramework.start(Test003::class.java) }
        val componentClasses = components.map { it.javaClass }

        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.valid.test003.ServiceA::class.java)
        assertContains<Class<*>>(componentClasses, de.maucon.mauconframework.valid.test003.ServiceB::class.java)
    }
}