package de.maucon.mauconframework.di

import de.maucon.mauconframework.annotation.Injectable
import de.maucon.mauconframework.annotation.Qualifier
import de.maucon.mauconframework.di.data.ComponentDefinition
import de.maucon.mauconframework.di.data.DependencyQualifier
import java.lang.reflect.Method
import java.lang.reflect.Parameter

internal object ComponentDefinitionCreator {
    internal fun mapInjectableClassesToComponentDefinition(injectableClasses: Set<Class<*>>) =
        injectableClasses.map { mapInjectableClassToComponentDefinition(it) }

    internal fun mapConfigurationClassesToComponentDefinition(configurationClasses: Set<Class<*>>) =
        configurationClasses.flatMap { mapAllConfigurationClassComponentDefinitions(it) }

    internal fun mapParameterToDependencyQualifier(parameter: Parameter): DependencyQualifier {
        val name = parameter.getAnnotation(Qualifier::class.java)?.name?.lowercase()
        return DependencyQualifier(parameter.type, name)
    }

    private fun mapInjectableClassToComponentDefinition(clazz: Class<*>): ComponentDefinition {
        val injectable = clazz.getAnnotation(Injectable::class.java)!!
        val name = injectable.name.ifEmpty { clazz.simpleName }.lowercase()
        return mapClassToComponentDefinition(clazz, name)
    }

    private fun mapConfigurationClassToComponentDefinition(clazz: Class<*>): ComponentDefinition {
        val name = clazz.simpleName.lowercase()
        return mapClassToComponentDefinition(clazz, name)
    }

    private fun mapClassToComponentDefinition(clazz: Class<*>, name: String): ComponentDefinition {
        val classConstructor = clazz.constructors.first() // what when multiple constructors are found?

        val dependencies = mapParametersToDependencyQualifier(classConstructor.parameters.asList())
        val constructor = { args: Array<*> -> classConstructor.newInstance(*args) }

        return ComponentDefinition(clazz, name, dependencies, constructor)
    }

    private fun mapAllConfigurationClassComponentDefinitions(clazz: Class<*>): List<ComponentDefinition> {
        val classComponentDefinition = mapConfigurationClassToComponentDefinition(clazz)
        val methodComponentDefinitions = clazz.declaredMethods
            .filter { it.isAnnotationPresent(Injectable::class.java) }
            .map { mapMethodToComponentDefinition(it, classComponentDefinition) }

        return listOf(classComponentDefinition) + methodComponentDefinitions
    }

    private fun mapMethodToComponentDefinition(
        method: Method,
        classComponentDefinition: ComponentDefinition
    ): ComponentDefinition {
        val injectable = method.getAnnotation(Injectable::class.java)!!
        val name = injectable.name.ifEmpty { method.name }.lowercase()

        val classDependency = DependencyQualifier(classComponentDefinition.type, classComponentDefinition.name)
        val dependencies = listOf(classDependency) +
                mapParametersToDependencyQualifier(method.parameters.asList())

        val constructor = { args: Array<*> ->
            val (classObject, realArgs) = args[0] to args.drop(1).toTypedArray()
            method.invoke(classObject, *realArgs)
        }

        return ComponentDefinition(method.returnType, name, dependencies, constructor)
    }

    private fun mapParametersToDependencyQualifier(parameters: List<Parameter>) =
        parameters.map { mapParameterToDependencyQualifier(it) }
}