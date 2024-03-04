import Dependencies.appCompat
import Dependencies.coroutines
import Dependencies.espressoCore
import Dependencies.mockito
import Dependencies.mockitoKotlin
import Dependencies.mockitoInline
import Dependencies.robolectric
import Dependencies.fragment
import Dependencies.roomCompiler
import Dependencies.roomRuntime
import Dependencies.roomKtx
import Dependencies.picasso
import Dependencies.extJunit
import Dependencies.gson
import Dependencies.hilt
import Dependencies.hiltCompiler
import Dependencies.junit
import Dependencies.kotlinCore
import Dependencies.lifeCycle
import Dependencies.loggingInterceptor
import Dependencies.material
import Dependencies.navigationFragment
import Dependencies.navigationUI
import Dependencies.okHttp
import Dependencies.retrofit
import Dependencies.retrofitConverter
import ext.androidTestImplementation
import ext.implementation
import ext.kapt
import ext.testImplementation
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    /** General **/

    const val kotlinCore = "androidx.core:core-ktx:${Versions.kotlinCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"

    /** Testing **/

    const val junit = "junit:junit:${Versions.junit}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    /** Network **/

    val gson by lazy { "com.google.code.gson:gson:${Versions.gsonVersion}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
    val retrofitConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}" }
    val loggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}" }

    /** Storage **/

    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }

    /** DI **/

    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    /** Navigation **/

    val navigationUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}" }
}

/** Dependencies **/

fun DependencyHandler.general() {
    implementation(material)
    implementation(appCompat)
    implementation(lifeCycle)
    implementation(coroutines)
    implementation(kotlinCore)
    implementation(fragment)
    implementation(picasso)
}

fun DependencyHandler.testing() {
    testImplementation(junit)
    testImplementation(mockito)
    testImplementation(mockitoInline)
    testImplementation(mockitoKotlin)
    testImplementation(robolectric)
    androidTestImplementation(extJunit)
    androidTestImplementation(espressoCore)
}

fun DependencyHandler.network() {
    implementation(gson)
    implementation(okHttp)
    implementation(retrofit)
    implementation(loggingInterceptor)
    implementation(retrofitConverter)
}

fun DependencyHandler.storage() {
    implementation(roomRuntime)
    implementation(roomKtx)
    kapt(roomCompiler)
}

fun DependencyHandler.di() {
    kapt(hiltCompiler)
    implementation(hilt)
}

fun DependencyHandler.navigation() {
    implementation(navigationUI)
    implementation(navigationFragment)
}
