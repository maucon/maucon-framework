package de.maucon.mauconframework.di

internal data class ComponentDescriptor(
    val qualifier: ComponentQualifier,
    val dependencies: List<ComponentQualifier>,
    val constructor: (Array<*>) -> Any
)

internal data class ComponentQualifier(
    val type: Class<*>,
    val qualifier: String
) {
    override fun toString() = "${type.simpleName} ($type)"
}