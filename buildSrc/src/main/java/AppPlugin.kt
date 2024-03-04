import org.gradle.api.Plugin
import org.gradle.api.Project

open class AppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            setupPlugins()
            setupApp()
        }
    }

    private fun Project.setupPlugins() = plugins.apply {
        apply("com.android.application")
        apply("com.google.dagger.hilt.android")
        apply("kotlin-android")
        apply("kotlin-parcelize")
        apply("maven-publish")
        apply("kotlin-kapt")
    }

    companion object {
        const val PLUGIN_APP = "app-plugin"
    }
}
