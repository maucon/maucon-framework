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
 * The main entry point for starting the dependency injection and instantiation process.
 *
 * This object serves as the starting point for the dependency injection framework.
 * It contains the `start` function, which is used to initiate the process of
 * instantiating classes and injecting their dependencies based on the provided configuration.
 *
 * @see start
 */
object MauConFramework {
    private val log = LoggerFactory.getLogger(MauConFramework::class.java)

    /**
     * Starts the dependency injection process by scanning the provided `baseClass` and all its subfolders,
     * instantiating the classes, and injecting their dependencies.
     *
     * This method performs a component scan starting from the `baseClass`, including all subfolders (or packages).
     * It processes the injectable components marked with `@Injectable`, resolves their dependencies, and returns
     * a collection of instantiated objects with their dependencies injected.
     *
     * The `baseClass` serves as the root of the scanning process, and all classes in its package and subpackages
     * will be scanned for injectable components.
     *
     * @param baseClass The root class from which the component scan begins. This class and all classes in its
     *                  package and subpackages will be scanned for components annotated with `@Injectable`.
     *
     * @return A collection of instantiated objects that have been injected with their dependencies.

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