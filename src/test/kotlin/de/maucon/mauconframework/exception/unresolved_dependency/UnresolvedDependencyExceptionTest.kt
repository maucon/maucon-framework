package de.maucon.mauconframework.exception.unresolved_dependency

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.exception.ResolveComponentException
import de.maucon.mauconframework.di.exception.UnresolvedDependencyException
import de.maucon.mauconframework.exception.unresolved_dependency.test002.Test002
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

@DisplayName("Unresolved dependency exceptions")
class UnresolvedDependencyExceptionTest {
    @Test
    fun test002() {
        val exception = assertThrows<ResolveComponentException> { MauConFramework.start(Test002::class.java) }
        assertEquals(UnresolvedDependencyException::class, exception.cause!!::class)
    }
}