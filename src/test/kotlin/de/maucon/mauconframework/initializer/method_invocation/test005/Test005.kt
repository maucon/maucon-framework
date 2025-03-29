package de.maucon.mauconframework.initializer.method_invocation.test005

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.initializer.Initializer
import de.maucon.mauconframework.di.annotation.Injectable

object Test005 {
    var subConfigInvocation = false
}

@Configuration
class SuperConfiguration : SubConfig()

open class SubConfig {
    @Initializer
    fun init(serviceA: ServiceA) {
        Test005.subConfigInvocation = true
    }
}

@Injectable
class ServiceA