import java.io.FileInputStream
import java.util.Properties

plugins {
    id(ModulePlugin.MODULE_NAME)
}

android {
    namespace = "com.raven.network"

    buildTypes {
        val localProperties = Properties()
        localProperties.load(FileInputStream(project.rootProject.file("local.properties")))

        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.nytimes.com/\"")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("api.key")}\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.nytimes.com/\"")
            buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("api.key")}\"")
        }
    }
    buildFeatures.buildConfig = true
}

dependencies {
    di()
    general()
    network()
}
