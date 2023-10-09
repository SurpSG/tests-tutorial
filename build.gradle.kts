plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"

    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"

    `jvm-test-suite`
    `java-test-fixtures`
}

group = "com.tests"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_19
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":employee"))

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("io.projectreactor:reactor-core")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")

//    testImplementation(platform("org.junit:junit-bom:5.10.0"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
//    testImplementation("com.willowtreeapps.assertk:assertk:0.27.0") // https://github.com/ajax-systems/testing-libs-for-kotlin
//    testImplementation("io.mockk:mockk:1.13.8")
//    testImplementation("org.awaitility:awaitility:4.2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

testing.suites {
//
//    val test by getting(JvmTestSuite::class) {
//        useJUnitJupiter()
//        dependencies {
//            implementation("com.willowtreeapps.assertk:assertk:0.27.0")
//            implementation("io.mockk:mockk:1.13.8")
//
//            implementation("io.projectreactor:reactor-test")
//            implementation("org.awaitility:awaitility:4.2.0")
//        }
//    }
//
//    register<JvmTestSuite>("integrationTest") {
//        useJUnitJupiter()
//        dependencies {
//            implementation(project())
//            implementation("com.willowtreeapps.assertk:assertk:0.27.0")
//
//            implementation("org.springframework.boot:spring-boot-starter-test")
//
//            implementation("io.projectreactor:reactor-test")
//            implementation("org.awaitility:awaitility:4.2.0")
//        }
//    }
}


