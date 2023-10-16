plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"

    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"

    `java-test-fixtures`
    id("io.github.surpsg.delta-coverage") version "1.2.0"
}

group = "com.tests"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_18
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

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("io.nats:jnats:2.16.14")
//    testImplementation("com.willowtreeapps.assertk:assertk:0.27.0") // https://github.com/ajax-systems/testing-libs-for-kotlin
//    testImplementation("io.mockk:mockk:1.13.8")
//    testImplementation("org.awaitility:awaitility:4.2.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configure<io.github.surpsg.deltacoverage.gradle.DeltaCoverageConfiguration> {
    diffSource.git.compareWith("master")

    violationRules.failIfCoverageLessThan(0.9)
    reports {
        html.set(true)
    }
}

tasks.test {
    testLogging { showStandardStreams = true }
    dependsOn("employee:test")
}

fun sourceFromJacoco(jacocoSource: JacocoReportBase.() -> FileCollection): FileCollection {
    val sourcesProvider: Provider<FileCollection> = provider {
        subprojects.asSequence()
            .map { it.tasks.findByName("jacocoTestReport") }
            .filterNotNull()
            .map { it as JacocoReportBase }
            .map { it.jacocoSource() }
            .fold(files() as FileCollection) { all, next ->
                all + next
            }
    }
    return files(sourcesProvider)
}

tasks.register<JacocoReport>("jacocoRootReport") {
    group = "verification"
    description = "Generates an aggregate report from all subprojects"

    sourceDirectories.from(sourceFromJacoco { sourceDirectories })
    classDirectories.from(sourceFromJacoco { classDirectories })
    executionData.from(sourceFromJacoco { executionData })

    reports {
        html.required.set(true)
    }
}
