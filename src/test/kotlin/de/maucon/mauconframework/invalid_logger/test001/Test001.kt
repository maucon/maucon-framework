package de.maucon.mauconframework.invalid_logger.test001

import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Logging

class Test001

@Injectable
class ServiceA

@Injectable
class ServiceB(
    @Logging private val log: ServiceA
)