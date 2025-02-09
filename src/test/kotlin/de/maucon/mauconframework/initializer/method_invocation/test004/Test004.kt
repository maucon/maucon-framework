package de.maucon.mauconframework.initializer.method_invocation.test004

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.initializer.Initializer

object Test004 {
    var subConfigInvocation = false
}

@Configuration
class SuperConfiguration : SubConfig()

open class SubConfig {
    @Initializer
    fun init() {
        Test004.subConfigInvocation = true
    }
}
