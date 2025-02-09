package de.maucon.mauconframework.di.component_instantiation.test002

import de.maucon.mauconframework.di.annotation.Injectable

@Injectable
class ServiceA() {
    init {
        throw IllegalArgumentException("this is a test")
    }
}