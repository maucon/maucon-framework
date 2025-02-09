package de.maucon.mauconframework.initializer.unresolved_dependency

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.initializer.exception.InitializeMethodUnresolvedDependencyException
import de.maucon.mauconframework.initializer.unresolved_dependency.test001.Test001
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Initializer method unresolved dependency exceptions")
class InitializerMethodUnresolvedDependencyTest {
    @Test
    fun test001() {
        val exception = assertThrows<InitializeMethodUnresolvedDependencyException> { MauConFramework.start(Test001::class.java) }
        exception.printStackTrace()
    }
}