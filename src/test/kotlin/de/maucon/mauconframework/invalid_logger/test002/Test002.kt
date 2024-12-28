package de.maucon.mauconframework.invalid_logger.test002

import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Logging

class Test002

@Injectable
class ServiceA(
    @Logging private val log: String
)