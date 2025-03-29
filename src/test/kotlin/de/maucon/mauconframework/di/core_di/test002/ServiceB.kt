package de.maucon.mauconframework.di.core_di.test002

import de.maucon.mauconframework.di.annotation.Injectable

@Injectable
class ServiceB(
    private val serviceA: de.maucon.mauconframework.di.core_di.test002.ServiceA
)