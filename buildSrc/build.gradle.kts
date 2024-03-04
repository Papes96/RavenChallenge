import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("com.squareup:javapoet:1.13.0")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}

gradlePlugin {
    plugins {
        register("app-plugin") {
            id = "app-plugin"
            implementationClass = "AppPlugin"
        }

        register("module-plugin") {
            id = "module-plugin"
            implementationClass = "ModulePlugin"
        }
    }
}
