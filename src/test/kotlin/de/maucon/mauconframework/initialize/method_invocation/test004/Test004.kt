package de.maucon.mauconframework.initialize.method_invocation.test004

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Initialize

object Test004 {
    var subConfigInvocation = false
}

@Configuration
class SuperConfiguration : SubConfig()

open class SubConfig {
    @Initialize
    fun init() {
        Test004.subConfigInvocation = true
    }
}
