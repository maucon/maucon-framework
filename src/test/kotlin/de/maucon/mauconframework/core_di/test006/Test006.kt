package de.maucon.mauconframework.core_di.test006

import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Logging
import org.slf4j.Logger

class Test006

@Injectable
class ServiceA

@Injectable
class ServiceB(
    @Logging private val log: Logger,
    private val serviceA: ServiceA
) {
    init {
        log.error("this is a test")
    }
}