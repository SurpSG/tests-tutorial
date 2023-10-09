plugins {
    kotlin("jvm") version "1.9.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
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
    )
}
