package de.maucon.mauconframework.core_di.test007

import de.maucon.mauconframework.annotation.Configuration
import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Logging
import org.slf4j.Logger

class Test007

@Configuration
class ConfigurationA(
    @Logging private val log: Logger
) {
    @Injectable
    fun stuff(
        @Logging log2: Logger
    ): Int {
        log.error("log")
        log2.error("log2")

        return 1
    }
}