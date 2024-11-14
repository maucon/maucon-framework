package de.maucon.mauconframework.initialize.method_invocation.test002

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Initialize
import de.maucon.mauconframework.annotation.Injectable

class Test002

@Configuration
class ConfigurationA(
    val serviceA: ServiceA
) {
    @Initialize
    fun init(serviceA: ServiceA, configurationA: ConfigurationA) {
        println("Configuration A")
    }
}

@Injectable
class ServiceA {
    @Initialize
    fun init(configurationA: ConfigurationA) {
        println("Service A")
    }
}