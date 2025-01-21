package de.maucon.mauconframework.core_di.test008

import de.maucon.mauconframework.annotation.Initialize
import de.maucon.mauconframework.annotation.Injectable

class Test008

@Injectable
object ObjectA {
    fun test() {
        println("hello from object")
    }
}

@Injectable
class ClassB(
    private val objectA: ObjectA
) {
    @Initialize
    fun init() {
        objectA.test()
    }
}
