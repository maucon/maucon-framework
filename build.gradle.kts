plugins {
    kotlin("jvm") version "2.0.0"
}

group = "de.maucon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")

    testImplementation(kotlin("test"))
    testImplementation("org.slf4j:slf4j-api:2.0.16")
    testImplementation("ch.qos.logback:logback-classic:1.5.8")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}