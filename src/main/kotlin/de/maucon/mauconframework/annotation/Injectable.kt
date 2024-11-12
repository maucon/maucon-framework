package de.maucon.mauconframework.annotation

// 1. name of annotation if set
// 2. name of parameter if applicable
// 3. name of class
// all lowercased
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Injectable(
    val name: String = ""
)