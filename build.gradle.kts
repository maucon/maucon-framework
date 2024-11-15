plugins {
    kotlin("jvm") version "2.0.21"
    `maven-publish`
}

group = "de.maucon"
version = "1.3.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")

    testImplementation(kotlin("test"))
    testImplementation("org.slf4j:slf4j-api:2.0.16")
    testImplementation("ch.qos.logback:logback-classic:1.5.12")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.register("printVersion") {
    doLast {
        println(version)
    }
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/maucon/maucon-framework")

            credentials {
                username = "maucon"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}