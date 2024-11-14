package de.maucon.mauconframework.core_di.test004

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Injectable

class Test004

@Configuration
class ConfigurationA(
    val serviceA: ServiceA
)

@Injectable
class ServiceA