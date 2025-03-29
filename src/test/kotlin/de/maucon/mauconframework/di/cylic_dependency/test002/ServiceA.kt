package de.maucon.mauconframework.di.cylic_dependency.test002

import de.maucon.mauconframework.di.annotation.Injectable

@Injectable
class ServiceA(
    private val serviceB: ServiceB
)