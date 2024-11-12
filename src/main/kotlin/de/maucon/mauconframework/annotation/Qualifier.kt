package de.maucon.mauconframework.annotation

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Qualifier(
    val name: String
)