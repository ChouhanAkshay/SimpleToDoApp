// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    val compose_version by extra{ "1.1.0" }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
    }
}

tasks.register("create", Delete::class){
    delete(rootProject.buildDir)
}