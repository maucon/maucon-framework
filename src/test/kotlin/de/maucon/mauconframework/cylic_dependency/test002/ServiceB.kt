package de.maucon.mauconframework.cylic_dependency.test002

import de.maucon.mauconframework.annotation.Injectable

@Injectable
class ServiceB(
    private val serviceA: ServiceA
)