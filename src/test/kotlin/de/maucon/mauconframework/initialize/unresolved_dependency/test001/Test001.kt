package de.maucon.mauconframework.initialize.unresolved_dependency.test001

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Initialize

class Test001

@Configuration
class ConfigurationA {
    @Initialize
    fun init(configurationA: ConfigurationA, serviceA: ServiceA) {
    }
}

class ServiceA