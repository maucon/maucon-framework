package de.maucon.mauconframework.di.unresolved_dependency

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.exception.ResolveComponentException
import de.maucon.mauconframework.di.exception.UnresolvedDependencyException
import de.maucon.mauconframework.di.unresolved_dependency.test001.Test001
import de.maucon.mauconframework.di.unresolved_dependency.test002.Test002
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("Unresolved dependency exceptions")
class UnresolvedDependencyExceptionTest {
    @Test
    fun test001() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test001::class.java) }
        exception.printStackTrace()
        assertEquals(UnresolvedDependencyException::class, exception.cause!!::class)
    }

    @Test
    fun test002() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test002::class.java) }
        exception.printStackTrace()
        assertEquals(UnresolvedDependencyException::class, exception.cause!!::class)
    }
}