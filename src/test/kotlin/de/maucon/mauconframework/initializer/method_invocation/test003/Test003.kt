package de.maucon.mauconframework.initializer.method_invocation.test003

import de.maucon.mauconframework.initializer.Initializer
import de.maucon.mauconframework.di.annotation.Injectable

object Test003 {
    val invocationOrder = mutableListOf<Int>()
}

@Injectable
class Service1 {
    @Initializer
    fun init() {
        Test003.invocationOrder.add(1)
    }
}

@Injectable
class Service2 {
    @Initializer(priority = 4)
    fun init() {
        Test003.invocationOrder.add(2)
    }
}

@Injectable
class Service3 {
    @Initializer(priority = 3)
    fun init() {
        Test003.invocationOrder.add(3)
    }
}

@Injectable
class Service4 {
    @Initializer(priority = 2)
    fun init() {
        Test003.invocationOrder.add(4)
    }
}

@Injectable
class Service5 {
    @Initializer(priority = 1)
    fun init() {
        Test003.invocationOrder.add(5)
    }
}