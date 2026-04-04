package de.maucon.mauconframework

import de.maucon.mauconframework.MauConFramework.start
import de.maucon.mauconframework.command.CommandHandler
import de.maucon.mauconframework.command.service.CommandService
import de.maucon.mauconframework.di.DependencyInjector
import de.maucon.mauconframework.di.annotation.Injectable
import de.maucon.mauconframework.event.EventSubscriber
import de.maucon.mauconframework.event.service.EventService
import de.maucon.mauconframework.initializer.Initializer
import de.maucon.mauconframework.initializer.InitializerService
import org.slf4j.LoggerFactory

/**
 * Entry point for initializing and managing the dependency injection framework.
 *
 * This object serves as the core of the DI framework. It is responsible for:
 * - Scanning packages for injectable components
 * - Instantiating component classes annotated with [Injectable]
 * - Injecting dependencies into component fields and constructors
 * - Registering event subscribers ([EventSubscriber]) and command handlers ([CommandHandler])
 * - Executing component initializers ([Initializer])
 *
 * The framework provides a centralized starting point via the [start] function,
 * which triggers the full initialization process.
 *
 * ### Usage
 * ```kotlin
 * val components = MauConFramework.start(MyApplication::class.java)
 * ```
 *
 * @see Injectable
 * @see EventSubscriber
 * @see CommandHandler
 * @see Initializer
 */
object MauConFramework {
    private val log = LoggerFactory.getLogger(MauConFramework::class.java)

    /**
     * Initializes the dependency injection framework starting from the package
     * of the given [baseClass].
     *
     * This function performs the following steps:
     * 1. Scans the package of [baseClass] and all subpackages for classes annotated with [Injectable].
     * 2. Instantiates each component and resolves its dependencies.
     * 3. Executes any component initializers defined in the system.
     * 4. Registers event subscribers and command handlers found among the components.
     *
     * @param baseClass The root class whose package hierarchy will be scanned
     *                  for injectable components.
     * @return A collection of fully initialized component instances.
     *
     * @see Injectable
     * @see EventSubscriber
     * @see CommandHandler
     * @see Initializer
     */
    fun start(baseClass: Class<*>): Collection<Any> {
        val components = DependencyInjector.instantiateClasses(baseClass)

        InitializerService.runInitializers(components)
        EventService.registerEvents(components)
        CommandService.registerCommands(components)

        log.info("Started ${baseClass.simpleName}!")
        return components.values
    }
}