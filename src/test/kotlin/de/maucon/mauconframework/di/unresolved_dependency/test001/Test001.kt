package de.maucon.mauconframework.di.unresolved_dependency.test001

import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.di.annotation.Qualifier

class Test001

@Injectable
class ServiceA(@Qualifier("serviceB") val serviceB: ServiceB)

class ServiceB