import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.freefair.lombok") version "8.10.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-function-web")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
val shadowTasks = tasks.withType<ShadowJar> {
    archiveClassifier = "aws"
    dependencies {
        exclude(dependency("org.springframework.cloud:spring-cloud-function-web:4.1.1"))
        exclude(dependency("org.springframework.boot:spring-boot-starter-web"))
    }
    mergeServiceFiles()
    append("META-INF/spring.handlers")
    append("META-INF/spring.schemas")
    append("META-INF/spring.tooling")
    append("META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports")
    append("META-INF/spring/org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration.imports")
    transform(PropertiesFileTransformer::class.java) {
        paths = listOf("META-INF/spring.factories")
        mergeStrategy = "append"
    }
    manifest {
        attributes["Main-Class"] = "dev.tarasov.App"
    }
}

tasks.named("assemble") {
    dependsOn(shadowTasks)
}