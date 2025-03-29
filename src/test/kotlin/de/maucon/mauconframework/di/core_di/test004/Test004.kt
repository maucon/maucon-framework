package de.maucon.mauconframework.di.core_di.test004

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.di.annotation.Injectable

class Test004

@Configuration
class ConfigurationA(
    val serviceA: de.maucon.mauconframework.di.core_di.test004.ServiceA
)

@Injectable
class ServiceA