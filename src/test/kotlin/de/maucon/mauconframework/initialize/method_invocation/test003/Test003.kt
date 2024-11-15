package de.maucon.mauconframework.initialize.method_invocation.test003

import de.maucon.mauconframework.annotation.Initialize
import de.maucon.mauconframework.annotation.Injectable

object Test003 {
    val invocationOrder = mutableListOf<Int>()
}

@Injectable
class Service1 {
    @Initialize
    fun init() {
        Test003.invocationOrder.add(1)
    }
}

@Injectable
class Service2 {
    @Initialize(priority = 4)
    fun init() {
        Test003.invocationOrder.add(2)
    }
}

@Injectable
class Service3 {
    @Initialize(priority = 3)
    fun init() {
        Test003.invocationOrder.add(3)
    }
}

@Injectable
class Service4 {
    @Initialize(priority = 2)
    fun init() {
        Test003.invocationOrder.add(4)
    }
}

@Injectable
class Service5 {
    @Initialize(priority = 1)
    fun init() {
        Test003.invocationOrder.add(5)
    }
}