import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import config.ProjectConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

val Project.libraryExtension: LibraryExtension
    get() = extensions.findByType(LibraryExtension::class.java)
        ?: error("$name is not an Android library module")

val Project.androidExtension: AppExtension
    get() = extensions.findByType(AppExtension::class.java)
        ?: error("$name is not an Android module")

fun BaseExtension.commonSetup() {
    compileSdkVersion(ProjectConfig.compileSdk)
    defaultConfig {
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        testInstrumentationRunner = "androidx.ext.test.runner.AndroidJUnitRunner"
    }

    (this as ExtensionAware).configure<KotlinJvmOptions> {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        versionCode = 1
        versionName = "1.0.0"
    }
}

fun Project.setupApp() {
    androidExtension.run {
        commonSetup()
    }
}

fun Project.setupModule() {
    libraryExtension.run {
        commonSetup()
    }
}