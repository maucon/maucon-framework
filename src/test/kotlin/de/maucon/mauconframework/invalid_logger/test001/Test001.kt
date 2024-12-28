package de.maucon.mauconframework.invalid_logger.test001

import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Logging
import de.maucon.mauconframework.core_di.test006.ServiceA

class Test001

@Injectable
class ServiceA

@Injectable
class ServiceB(
    @Logging private val log: ServiceA
)