package de.maucon.mauconframework.initializer.method_invocation.test002

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.initializer.Initializer
import de.maucon.mauconframework.di.annotation.Injectable

class Test002

@Configuration
class ConfigurationA(
    val serviceA: ServiceA
) {
    @Initializer
    fun init(serviceA: ServiceA, configurationA: ConfigurationA) {
        println("Configuration A")
    }
}

@Injectable
class ServiceA {
    @Initializer
    fun init(configurationA: ConfigurationA) {
        println("Service A")
    }
}