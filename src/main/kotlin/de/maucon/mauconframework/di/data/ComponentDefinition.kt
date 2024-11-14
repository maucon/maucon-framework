package de.maucon.mauconframework.di.data

/**
 * A definition of an initializable component with dependencies
 **/
internal data class ComponentDefinition(
    val type: Class<*>,
    val name: String,
    val dependencies: List<DependencyQualifier>,
    val constructor: (Array<*>) -> Any
) {
    override fun toString() = "'$name': ${type.simpleName} ($type)"
}