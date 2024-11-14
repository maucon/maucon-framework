package de.maucon.mauconframework.core_di.test001

import de.maucon.mauconframework.annotation.Injectable

class Test001 {
    @Injectable
    class ServiceA

    @Injectable
    class ServiceB(
        private val serviceA: ServiceA
    )
}