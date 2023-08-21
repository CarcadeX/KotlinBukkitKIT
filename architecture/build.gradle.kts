plugins {
    id("java")
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
    id("signing")
}

group = "me.redtea"
val globalArtifactId = "kotlinbukkitkit-architecture"
val globalName = "kotlinbukkitkit-architecture"

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
            groupId = "io.github.iredtea"
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
                groupId = "io.github.iredtea"
                version = version
                name.set(globalArtifactId)
                description.set(globalArtifactId)
                url.set("https://github.com/iRedTea/CarcadeX")

                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
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
                    url.set("https://github.com/iRedTea/CarcadeX")
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
