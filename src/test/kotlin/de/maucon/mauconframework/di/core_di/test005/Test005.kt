package de.maucon.mauconframework.di.core_di.test005

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.di.annotation.Injectable

class Test005

@Configuration
class SuperConfiguration : de.maucon.mauconframework.di.core_di.test005.SubConfiguration()

open class SubConfiguration {
    @Injectable
    fun serviceA() = de.maucon.mauconframework.di.core_di.test005.ServiceA()
}

class ServiceA