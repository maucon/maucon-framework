package de.maucon.mauconframework.di.core_di.test006

import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.di.annotation.Logging
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