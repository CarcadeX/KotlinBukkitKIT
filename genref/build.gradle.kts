plugins {
    id("java")
    kotlin("jvm")
    id("maven-publish")
    id("signing")
    id("com.google.devtools.ksp") version "1.9.0-1.0.11"
}

group = "tech.carcadex"
val globalArtifactId = "kotlinbukkitkit-genref"
val globalName = "kotlinbukkitkit-genref"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation(project(":architecture"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.0-1.0.11")
    implementation("com.squareup:kotlinpoet:1.10.1")
    implementation("com.squareup:kotlinpoet-ksp:1.10.1")

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
