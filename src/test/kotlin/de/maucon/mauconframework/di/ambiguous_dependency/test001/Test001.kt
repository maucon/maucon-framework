package de.maucon.mauconframework.di.ambiguous_dependency.test001

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.di.annotation.Injectable

class Test001

@Configuration
class ConfigurationA {
    @Injectable
    fun listOne(): List<String> = listOf("1")

    @Injectable
    fun listThree(listOne: List<String>): List<Int> = listOf(3)
}

@Injectable
class ServiceA(val listOne: List<String>)