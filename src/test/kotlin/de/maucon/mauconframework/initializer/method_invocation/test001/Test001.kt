package de.maucon.mauconframework.initializer.method_invocation.test001

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.initializer.Initializer
import de.maucon.mauconframework.di.annotation.Injectable

class Test001

@Configuration
class ConfigurationA(
    val serviceA: ServiceA
) {
    @Initializer
    fun init(serviceA: ServiceA, configurationA: ConfigurationA) {
        throw IllegalStateException("this is a test")
    }
}

@Injectable
class ServiceA {
    @Initializer
    fun init(configurationA: ConfigurationA) {
        println("Service A")
    }
}