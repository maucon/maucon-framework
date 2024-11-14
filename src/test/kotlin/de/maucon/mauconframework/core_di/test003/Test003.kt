package de.maucon.mauconframework.core_di.test003

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Injectable

class Test003

@Configuration
class ConfigurationA {
    @Injectable
    fun serviceB() = ServiceB()
}

@Injectable
class ServiceA(val serviceB: ServiceB)

class ServiceB