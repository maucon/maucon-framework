package de.maucon.mauconframework

import de.maucon.mauconframework.di.DependencyInjector

object MauConFramework {
    fun start(baseClass: Class<*>): Collection<Any> {
        return DependencyInjector.instantiateClasses(baseClass)
    }
}