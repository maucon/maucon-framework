package de.maucon.mauconframework.exception.component_instantiation.test002

import de.maucon.mauconframework.annotation.Injectable

@Injectable
class ServiceA() {
    init {
        throw IllegalArgumentException("this is a test")
    }
}