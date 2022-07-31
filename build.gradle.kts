import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
}

group = "com.pvpbeach.kotori"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.velocitypowered.com/snapshots/")
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("com.velocitypowered:velocity-api:1.0.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:1.0.0-SNAPSHOT")

    implementation("com.github.Revxrsal.Lamp:common:3.0.71")
    implementation("com.github.Revxrsal.Lamp:velocity:3.0.71")
    implementation("com.google.code.gson:gson:2.9.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}