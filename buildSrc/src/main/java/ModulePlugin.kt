import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import config.ProjectConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

open class ModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            setupPlugins()
            setupModule()
        }
    }

    private fun Project.setupPlugins() = plugins.apply {
        apply("com.android.library")
        apply("com.google.dagger.hilt.android")
        apply("kotlin-android")
        apply("kotlin-parcelize")
        apply("kotlin-kapt")
    }

    companion object {
        const val MODULE_NAME = "module-plugin"
    }
}
