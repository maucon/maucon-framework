package de.maucon.mauconframework.di.core_di.test001

import de.maucon.mauconframework.di.annotation.Injectable

class Test001 {
    @Injectable
    class ServiceA

    @Injectable
    class ServiceB(
        private val serviceA: de.maucon.mauconframework.di.core_di.test001.Test001.ServiceA
    )
}