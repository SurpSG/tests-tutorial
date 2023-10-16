plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"

    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"

    jacoco
    `java-test-fixtures`
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")

    implementation("io.projectreactor:reactor-core")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    implementation("io.nats:jnats:2.16.14")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.awaitility:awaitility:4.2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
