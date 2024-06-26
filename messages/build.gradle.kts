import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin("jvm")
    id("maven-publish")
    id("signing")
}

group = "tech.carcadex"
val globalArtifactId = "kotlinbukkitkit-messages"
val globalName = "kotlinbukkitkit-messages"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    compileOnly("dev.triumphteam:triumph-gui:3.1.2")

    compileOnly("com.github.cryptomorin:XSeries:9.2.0") //add to libs
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    implementation(files(parent!!.files("messages/libs/MiniMessages.jar"))) //using universal minimessages,
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = globalArtifactId
            groupId = "tech.carcadex"
            version = version
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                artifactId = globalArtifactId
                groupId = "tech.carcadex"
                version = version
                name.set(globalArtifactId)
                description.set(globalArtifactId)
                url.set("https://github.com/CarcadeX/KotlinBukkitKit")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://mit-license.org/")
                    }
                }
                developers {
                    developer {
                        id.set("itzRedTea")
                        name.set("Red Tea")
                        email.set("red__tea@outlook.com")
                    }
                }
                scm {
                    connection.set("scm:https://github.com/CarcadeX/$globalName.git")
                    developerConnection.set("git@github.com:CarcadeX/$globalName.git")
                    url.set("https://github.com/CarcadeX/KotlinBukkitKit")
                }
            }

        }
    }



    repositories {
        maven {
            val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                username = "itzRedTea"
                password = project.findProperty("password").toString()
            }
        }

    }
}

signing {
    useGpgCmd()
    sign(publishing.publications.getByName("maven"))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}