package de.maucon.mauconframework.di.core_di.test003

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.di.annotation.Injectable

class Test003

@Configuration
class ConfigurationA {
    @Injectable
    fun serviceB() = ServiceB()
}

@Injectable
class ServiceA(val serviceB: ServiceB)

class ServiceB