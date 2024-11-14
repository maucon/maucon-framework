package de.maucon.mauconframework.di.data

/**
 * A qualifier for a dependency of a component
 **/
internal data class DependencyQualifier(
    val type: Class<*>,
    val name: String?
) {
    override fun toString() = "'$name': ${type.simpleName} ($type)"
}