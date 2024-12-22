package de.maucon.mauconframework.core_di.test005

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Injectable

class Test005

@Configuration
class SuperConfiguration : SubConfiguration()

open class SubConfiguration {
    @Injectable
    fun serviceA() = ServiceA()
}

class ServiceA