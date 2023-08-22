import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.google.devtools.ksp") version "1.9.0-1.0.11"
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("xyz.jpenilla.run-paper") version "2.1.0"
}

group = "me.redtea"
version = "1.0.0-SNAPSHOT"

val carcadeXVersion = project.findProperty("carcadexversion").toString()


repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "kotlinminecraft"
        url = uri("https://raw.githubusercontent.com/KotlinMinecraft/KotlinBukkitAPI-Repository/main")
    }
}

dependencies {
    //Spigot 1.16.5 API
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")


    implementation(parent!!)

    //Kotlin modubles
    library("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    library("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    library("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    library("org.jetbrains.kotlin:kotlin-reflect:1.9.0")
    library("org.jetbrains:annotations:24.0.0")

    //XSeries
    library("com.github.cryptomorin:XSeries:9.2.0")

    //CarcadeX modules
    implementation(project(":architecture"))
    compileOnly(project(":genref"))
    ksp(project(":genref"))
    //implementation files('libs/MiniMessages-0.1.0.jar')
}


bukkit {
    main = "me.redtea.test.TestPlugin"
    author = "itzRedTea"
    apiVersion = "1.13"
}

tasks {
    runServer {
        minecraftVersion("1.16.5")
    }
}


tasks {
    shadowJar {
        dependencies {
            exclude {
                it.moduleGroup.startsWith("org.jetbrains")
            }
            exclude(dependency("io.github.iredtea:carcadex:*"))
            exclude(dependency("com.github.cryptomorin:XSeries:9.2.0"))
            exclude(dependency("net.kyori:adventure-api:4.14.0"))
        }
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}