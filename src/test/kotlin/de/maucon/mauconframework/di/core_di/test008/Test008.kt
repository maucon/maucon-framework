package de.maucon.mauconframework.di.core_di.test008

import de.maucon.mauconframework.initializer.Initializer
import de.maucon.mauconframework.di.annotation.Injectable

class Test008

@Injectable
object ObjectA {
    fun test() {
        println("hello from object")
    }
}

@Injectable
class ClassB(
    private val objectA: de.maucon.mauconframework.di.core_di.test008.ObjectA
) {
    @Initializer
    fun init() {
        de.maucon.mauconframework.di.core_di.test008.ObjectA.test()
    }
}
