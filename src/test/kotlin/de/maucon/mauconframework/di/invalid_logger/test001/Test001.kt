package de.maucon.mauconframework.di.invalid_logger.test001

import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.di.annotation.Logging

class Test001

@Injectable
class ServiceA

@Injectable
class ServiceB(
    @Logging private val log: ServiceA
)