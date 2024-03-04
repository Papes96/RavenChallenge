plugins {
    id(ModulePlugin.MODULE_NAME)
}

android {
    namespace = "com.raven.network"
}

dependencies {
    di()
    general()
    network()
}
