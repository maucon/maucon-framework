package de.maucon.mauconframework

import de.maucon.mauconframework.MauConFramework.start
import de.maucon.mauconframework.command.service.CommandService
import de.maucon.mauconframework.di.DependencyInjector
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.service.EventService
import de.maucon.mauconframework.initializer.InitializerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.slf4j.LoggerFactory

/**
 * Entry point for initializing and managing dependency injection.
 *
 * This object serves as the core of the dependency injection framework, responsible for
 * scanning, instantiating, and injecting dependencies into components based on the provided
 * configuration. It provides the `start` function to initiate the dependency resolution process.
 *
 * @see start
 */
object MauConFramework {
    private val log = LoggerFactory.getLogger(MauConFramework::class.java)

    /**
     * Initializes the dependency injection process by scanning the specified `baseClass` and its package hierarchy.
     *
     * This method performs a component scan starting from the `baseClass`, identifying classes annotated
     * with `@Injectable`, instantiating them, and injecting their dependencies. The scanning scope includes
     * all subpackages of the `baseClass`.
     *
     * Additionally, it executes initializers for components and registers event and command handlers.
     *
     * @param baseClass The root class defining the package scope for component scanning. All classes in the same package
     *                  and subpackages will be scanned for injectable components.
     * @param scope     The coroutine scope used for handling event-driven operations asynchronously.
     *
     * @return A collection of instantiated objects with their dependencies injected.
     *
     * @see Injectable
     */
    fun start(
        baseClass: Class<*>,
        scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    ): Collection<Any> {
        val components = DependencyInjector.instantiateClasses(baseClass)

        InitializerService.runInitializers(components)
        EventService.registerEvents(components, scope)
        CommandService.registerCommands(components)

        log.info("Started ${baseClass.simpleName}!")
        return components.values
    }
}