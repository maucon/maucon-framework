# MauCon-Framework [![Build](https://github.com/maucon/maucon-framework/actions/workflows/build_other.yml/badge.svg)](https://github.com/maucon/maucon-framework/actions/workflows/build_other.yml) [![Release](https://github.com/maucon/maucon-framework/actions/workflows/build_release.yml/badge.svg)](https://github.com/maucon/maucon-framework/actions/workflows/build_release.yml) [![](https://jitpack.io/v/maucon/maucon-framework.svg)](https://jitpack.io/#maucon/maucon-framework)

MauConFramework is a lightweight and flexible dependency injection (DI) framework written in Kotlin. It provides a simple yet powerful way to manage dependencies in your
Kotlin applications, making it easier to configure, instantiate, and inject dependencies across different components in your system.

In addition to the core DI functionality, **MauConFramework** also provides some useful stereotype classes such as `Repository` and `Entity`, which simplify the
management of entities and repositories in your application.

## Features

- **Dependency Injection**: Seamlessly inject dependencies into your classes and methods.
- **Configuration-based Setup**: Use `@Configuration` annotated classes to define the setup and injection of dependencies.
- **Named Injection**: Specify custom names for components to resolve dependencies by name using `@Injectable` and `@Qualifier` annotations.
- **Component Scanning**: Automatically scan and instantiate all classes in a given package and its subpackages.
- **Lightweight**: A minimal and non-intrusive framework that integrates easily into existing projects.
- **Stereotype Classes**: Provides `Repository` and `Entity` interfaces to simplify working with repositories and entities.

## Getting Started

### Gradle

Set up the JitPack repository:

```kotlin
repositories {
    maven("https://jitpack.io") {
        name = "jitpack-repo"
    }
}
```

Add the following dependency:

```kotlin
dependencies {
    implementation("com.github.maucon:maucon-framework:v1.9.0")
}
```