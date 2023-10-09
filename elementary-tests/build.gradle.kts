plugins {
    kotlin("jvm") version "1.9.10"
    jacoco

}

java {
    sourceCompatibility = JavaVersion.VERSION_18
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.willowtreeapps.assertk:assertk:0.27.0")
}

tasks.withType<Test> {
    useJUnitPlatform()

    systemProperties = mapOf(
        // parallel
        // https://junit.org/junit5/docs/snapshot/user-guide/#writing-tests-parallel-execution-config-properties
        "junit.jupiter.execution.parallel.enabled" to "true",
        "junit.jupiter.execution.parallel.config.fixed.parallelism" to "2",
        "junit.jupiter.execution.parallel.mode.default" to "concurrent",
        "junit.jupiter.execution.parallel.mode.classes.default" to "concurrent",
        "junit.jupiter.execution.timeout.default" to "2s",
        "junit.jupiter.execution.timeout.thread.mode.default" to "SEPARATE_THREAD",
    )
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}


