package de.maucon.mauconframework.cylic_dependency.test002

import de.maucon.mauconframework.annotation.Injectable

@Injectable
class ServiceA(
    private val serviceB: ServiceB
)