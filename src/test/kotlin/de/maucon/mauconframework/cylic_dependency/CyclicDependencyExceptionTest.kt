package de.maucon.mauconframework.cylic_dependency

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.exception.CyclicDependencyException
import de.maucon.mauconframework.di.exception.ResolveComponentException
import de.maucon.mauconframework.cylic_dependency.test002.Test002
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("Cyclic dependency exceptions")
class CyclicDependencyExceptionTest {
    @Test
    fun test002() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test002::class.java) }
        exception.printStackTrace()
        assertEquals(CyclicDependencyException::class, exception.cause!!::class)
    }
}