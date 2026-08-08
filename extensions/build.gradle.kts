plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":patches"))
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
