import config.ProjectConfig

plugins {
    id(AppPlugin.PLUGIN_APP)
}

android {
    namespace = ProjectConfig.appId

    viewBinding.enable = true
    dataBinding.enable = true

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    di()
    general()
    testing()
    navigation()

    implementation(project(":core"))
    implementation(project(":feature:home"))
    implementation(project(":common:network"))
}
