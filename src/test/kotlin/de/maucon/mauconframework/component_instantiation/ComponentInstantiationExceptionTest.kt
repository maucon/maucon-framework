package de.maucon.mauconframework.component_instantiation

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.component_instantiation.test001.Test001
import de.maucon.mauconframework.component_instantiation.test002.Test002
import de.maucon.mauconframework.di.exception.ComponentInstantiationException
import de.maucon.mauconframework.di.exception.ResolveComponentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("Component instantiation exceptions")
class ComponentInstantiationExceptionTest {
    @Test
    fun test001() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test001::class.java) }
        exception.printStackTrace()
        assertEquals(ComponentInstantiationException::class, exception.cause!!::class)
    }

    @Test
    fun test002() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test002::class.java) }
        exception.printStackTrace()
        assertEquals(ComponentInstantiationException::class, exception.cause!!::class)
    }
}