package de.maucon.mauconframework.di.ambiguous_dependency

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.ambiguous_dependency.test002.Test002
import de.maucon.mauconframework.di.exception.AmbiguousDependencyException
import de.maucon.mauconframework.di.exception.ResolveComponentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("Ambiguous dependency exceptions")
class AmbiguousDependencyExceptionTest {
    @Test
    fun test001() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(de.maucon.mauconframework.di.ambiguous_dependency.test001.Test001::class.java) }
        exception.printStackTrace()
        assertEquals(AmbiguousDependencyException::class, exception.cause!!::class)
    }

    @Test
    fun test002() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test002::class.java) }
        exception.printStackTrace()
        assertEquals(AmbiguousDependencyException::class, exception.cause!!::class)
    }
}