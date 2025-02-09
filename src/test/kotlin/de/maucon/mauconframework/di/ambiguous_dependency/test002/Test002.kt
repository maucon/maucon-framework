package de.maucon.mauconframework.di.ambiguous_dependency.test002

import de.maucon.mauconframework.di.annotation.Configuration
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.di.annotation.Qualifier

class Test002

@Configuration
class ConfigurationA {
    @Injectable
    fun listOne(): List<String> = listOf("1")

    @Injectable
    fun listThree(listOne: List<String>): List<Int> = listOf(3)
}

@Injectable
class ServiceA(@Qualifier("foo") val listOne: List<String>)