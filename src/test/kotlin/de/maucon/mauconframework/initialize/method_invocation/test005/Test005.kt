package de.maucon.mauconframework.initialize.method_invocation.test005

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Initialize
import de.maucon.mauconframework.annotation.Injectable

object Test005 {
    var subConfigInvocation = false
}

@Configuration
class SuperConfiguration : SubConfig()

open class SubConfig {
    @Initialize
    fun init(serviceA: ServiceA) {
        Test005.subConfigInvocation = true
    }
}

@Injectable
class ServiceA