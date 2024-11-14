package de.maucon.mauconframework.unresolved_dependency.test002

import de.maucon.mauconframework.annotation.Injectable

class Test002

@Injectable
class ServiceA(val serviceB: ServiceB)

class ServiceB