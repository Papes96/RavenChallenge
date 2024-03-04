import ext.implementation

plugins {
    id(ModulePlugin.MODULE_NAME)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.raven.home"

    viewBinding.enable = true
    dataBinding.enable = true

    buildFeatures.buildConfig = true

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    di()
    general()
    testing()
    network()
    storage()
    navigation()

    implementation(project(":core"))
}
