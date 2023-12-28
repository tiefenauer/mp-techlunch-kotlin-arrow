plugins {
    kotlin("jvm") version "1.9.21"
    id("com.google.devtools.ksp") version "1.9.21-1.0.16"
}

group = "info.tiefenauer"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.arrow.core)
    implementation(libs.arrow.optics)
    ksp(libs.arrow.optics.ksp.plugin) // needed for Arrow's optics: https://arrow-kt.io/learn/quickstart/#additional-setup-for-plug-ins

    testImplementation(libs.junit.api)
    testImplementation(libs.junit.params)
    testRuntimeOnly(libs.junit.engine)

    testImplementation(libs.assertj.core)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}