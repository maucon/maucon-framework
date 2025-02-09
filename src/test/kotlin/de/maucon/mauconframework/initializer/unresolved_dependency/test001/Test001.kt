package de.maucon.mauconframework.initializer.unresolved_dependency.test001

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.initializer.Initializer

class Test001

@Configuration
class ConfigurationA {
    @Initializer
    fun init(configurationA: ConfigurationA, serviceA: ServiceA) {
    }
}

class ServiceA