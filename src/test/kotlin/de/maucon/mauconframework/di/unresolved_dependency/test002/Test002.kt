package de.maucon.mauconframework.di.unresolved_dependency.test002

import de.maucon.mauconframework.di.annotation.Injectable

class Test002

@Injectable
class ServiceA(val serviceB: ServiceB)

class ServiceB