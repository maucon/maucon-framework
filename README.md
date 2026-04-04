# MauCon-Framework [![Build](https://github.com/maucon/maucon-framework/actions/workflows/build_other.yml/badge.svg)](https://github.com/maucon/maucon-framework/actions/workflows/build_other.yml) [![Release](https://github.com/maucon/maucon-framework/actions/workflows/build_release.yml/badge.svg)](https://github.com/maucon/maucon-framework/actions/workflows/build_release.yml)

MauCon-Framework is a lightweight and flexible Kotlin framework that combines **dependency injection (DI)**, **command handling**, **event-driven programming**, and **repository stereotypes
** into a unified
toolkit.

It provides an annotation-driven approach to dependency management, initialization, command dispatching, event publishing, and simple persistence patterns — all while remaining lightweight
and non-intrusive.

## Features

### Dependency Injection

- `@Configuration` – Mark classes as configuration sources for injectable components.
- `@Injectable` – Register classes or factory methods as injectable components.
- Named Injection – Use `@Injectable("name")` + `@Qualifier("name")` to resolve dependencies explicitly.
- Logger Injection – Inject `org.slf4j.Logger` automatically with `@Logging`.
- Initialization Hooks – Use `@Initializer` to run post-injection setup with priority ordering.

### Command Handling

- `@CommandHandler` – Annotate methods to handle commands, with support for priorities and cancellable commands.
- `CancellableCommand` – Base class for commands that can be stopped before execution.
- `CommandGateway` – Dispatch synchronous commands via `CommandGateway.apply(command)`.

### Event System

- `@EventSubscriber` – Register methods as event listeners automatically.
- `EventGateway` – Publish events with coroutine support.

### Stereotypes

- `Entity<ID>` – A base interface for domain entities with unique identifiers.
- `Repository<ID, ENTITY>` – A generic abstraction combining read and write operations.
- `ReadRepository` / `WriteRepository` – Focused contracts for read-only or write-only use cases.
- `InMemoryMapRepository` – A ready-to-use abstract repository that stores entities in memory using a map.

### Framework Entry Point

`MauConFramework.start()` – Scans, instantiates, and injects components starting from a given base class.
It also:

- Runs initializers (`@Initializer`)
- Registers event subscribers (`@EventSubscriber`)
- Registers command handlers (`@CommandHandler`)

## Getting Started

### Gradle

Set up the GitHub Maven repository with your credentials:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/maucon/maucon-framework")
        credentials {
            username = "<GITHUB_USERNAME>"
            password = "<GITHUB_TOKEN>"
        }
    }
}
```

Add the following dependency:

```kotlin
dependencies {
    implementation("com.github.maucon:maucon-framework:v2.0.0")
}
```

## Example Usage

### Bootstrapping the Framework

```kotlin
fun main() {
    MauConFramework.start(YourMainClass::class.java)
}
```

### Dependency Injection

```kotlin
@Injectable("customName")
class CustomService(val anotherService: Service) { ... }

@Configuration
class AppConfig {
    @Injectable
    fun anotherService(): Service {
        ...
    }
}
```

### Command Handling

```kotlin
@Injectable
class MyCommandHandler {
    @CommandHandler(priority = 1)
    fun handle(command: MyCommand) {
        println("Handled command: $command")
    }
}

data class MyCommand(val data: String) : CancellableCommand()

// Dispatching
val command = MyCommand("Hello")
CommandGateway.apply(command)
```

### Event System

```kotlin
@Injectable
class MyEventListener {
    @EventSubscriber
    fun on(event: MyEvent) {
        println("Received event: $event")
    }
}

data class MyEvent(val message: String)

// Publishing
EventGateway.publish(MyEvent("Hello Events"))
```

### Repository Stereotypes

```kotlin
data class User(override var id: Int, val name: String) : Entity<Int>

class UserRepository : InMemoryMapRepository<Int, User>()

// Usage
val repo = UserRepository()
repo.save(User(1, "Alice"))
repo.save(User(2, "Bob"))

println(repo.findAll()) // [User(id=1, name=Alice), User(id=2, name=Bob)]
println(repo.getById(1)) // User(id=1, name=Alice)
```