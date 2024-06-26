import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.20"
    id("io.codearte.nexus-staging") version "0.30.0"
}

group = "tech.carcadex"
version = project.findProperty("version").toString()

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("publishAll") {
    dependsOn(tasks.getByPath("architecture:publish"))
    dependsOn(tasks.getByPath("extensions:publish"))
    dependsOn(tasks.getByPath("serialization:publish"))
    dependsOn(tasks.getByPath("genref:publish"))
    dependsOn(tasks.getByPath("messages:publish"))
    dependsOn(tasks.getByPath("commands:publish"))
    dependsOn(tasks.getByPath("utility:publish"))
    dependsOn(tasks.getByPath("exposed:publish"))
    dependsOn(tasks.getByPath("thirdparty:publish"))
    dependsOn(tasks.getByPath("menu:publish"))
}