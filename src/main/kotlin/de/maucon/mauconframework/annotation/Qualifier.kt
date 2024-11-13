package de.maucon.mauconframework.annotation

/**
 * Specifies a named qualifier for dependency injection, allowing for named dependency resolution.
 *
 * This annotation is applied to constructor or function parameters to indicate which specific
 * injectable component should be injected based on its `name`.
 *
 * The [Qualifier] annotation works in conjunction with the [Injectable] annotation. When a component
 * is annotated with `@Injectable(name = "customName")`, dependent parameters can use `@Qualifier("customName")`
 * to request that specific component.
 *

 *
 * Example usage:
 * ```
 * @Injectable("customName")
 * class CustomService { ... }
 *
 * @Injectable
 * class Consumer(@Qualifier("customName") val service: CustomService)
 * ```
 *
 * @property name The name of the component to resolve, which should match the `name` specified in
 * the [Injectable] annotation.
 *
 * @see Injectable
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Qualifier(
    val name: String
)