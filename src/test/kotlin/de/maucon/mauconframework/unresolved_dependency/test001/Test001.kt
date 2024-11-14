package de.maucon.mauconframework.unresolved_dependency.test001

import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Qualifier

class Test001

@Injectable
class ServiceA(@Qualifier("serviceB") val serviceB: ServiceB)

class ServiceB