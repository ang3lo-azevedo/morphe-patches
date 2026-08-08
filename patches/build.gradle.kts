group = "app.ang3lo.morphe-patches"

patches {
    about {
        name = "Ang3lo's Patches"
        description = "Patches for apps I use and maintain"
        source = "https://github.com/ang3lo-azevedo/morphe-patches"
        author = "ang3lo-azevedo"
        contact = "https://github.com/ang3lo-azevedo"
        website = "https://github.com/ang3lo-azevedo/morphe-patches"
        license = "GPLv3"
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

val generatedSecretsDir = layout.buildDirectory.dir("generated/secrets/kotlin")

sourceSets {
    main {
        kotlin.srcDir(generatedSecretsDir)
    }
}

val generateSecrets by tasks.registering {
    inputs.property("PLACEHOLDER", "")
    outputs.dir(generatedSecretsDir)

    doLast {
        val outputDir = generatedSecretsDir.get().asFile.resolve("app/template/patches/shared")
        outputDir.mkdirs()
        outputDir.resolve("BuildSecrets.kt").writeText(
            """
            package app.template.patches.shared

            internal object BuildSecrets {
            }
            """.trimIndent(),
        )
    }
}

tasks.named("compileKotlin") {
    dependsOn(generateSecrets)
}

tasks.named("sourcesJar") {
    dependsOn(generateSecrets)
}

val patchListGeneratorClasspath: Configuration by configurations.creating

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"

        dependsOn(build)

        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    publish {
        dependsOn("generatePatchesList")
    }
}
