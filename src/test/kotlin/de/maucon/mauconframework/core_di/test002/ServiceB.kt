package de.maucon.mauconframework.core_di.test002

import de.maucon.mauconframework.annotation.Injectable

@Injectable
class ServiceB(
    private val serviceA: ServiceA
)