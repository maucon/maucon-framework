package de.maucon.mauconframework.component_instantiation.test001

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Injectable

class Test001

@Injectable
class ServiceB(val serviceA: ServiceA)

@Configuration
class ConfigurationA {
    @Injectable
    fun serviceA(): ServiceA {
        throw IllegalStateException("this is a test")
    }
}

class ServiceA
