plugins {
    id(ModulePlugin.MODULE_NAME)
}

android {
    namespace = "com.raven.core"
}

dependencies {
    di()
    general()
    testing()
}
