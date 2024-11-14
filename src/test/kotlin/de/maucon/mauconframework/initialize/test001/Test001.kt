package de.maucon.mauconframework.initialize.test001

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Initialize
import de.maucon.mauconframework.annotation.Injectable

class Test001

@Configuration
class ConfigurationA(
    val serviceA: ServiceA
) {
    @Initialize
    fun init(serviceA: ServiceA, configurationA: ConfigurationA) {
        throw IllegalStateException("this is a test")
    }
}

@Injectable
class ServiceA {
    @Initialize
    fun init(configurationA: ConfigurationA) {
        println("Service A")
    }
}