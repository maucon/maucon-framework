package de.maucon.mauconframework.initialize.unresolved_dependency

import de.maucon.mauconframework.MauConFramework
import de.maucon.mauconframework.di.exception.InitializeMethodUnresolvedDependencyException
import de.maucon.mauconframework.initialize.unresolved_dependency.test001.Test001
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("Initialize method unresolved dependency exceptions")
class InitializeMethodUnresolvedDependencyTest {
    @Test
    fun test001() {
        val exception = assertThrows<InitializeMethodUnresolvedDependencyException> { MauConFramework.start(Test001::class.java) }
        exception.printStackTrace()
    }
}