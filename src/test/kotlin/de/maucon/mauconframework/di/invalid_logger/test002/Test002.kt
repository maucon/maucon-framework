package de.maucon.mauconframework.di.invalid_logger.test002

import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.di.annotation.Logging

class Test002

@Injectable
class ServiceA(
    @Logging private val log: String
)