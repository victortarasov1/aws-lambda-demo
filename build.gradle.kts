plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("org.postgresql:postgresql:42.7.4")
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveClassifier.set("")
}