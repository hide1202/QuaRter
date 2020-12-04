object Kotlin {
    const val version = "1.4.10"
    const val coroutineVersion = "1.4.2"

    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
}

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "7.0.0-alpha01"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
}

object Android {
    const val compileSdk = 30
    const val minSdk = 21
    const val targetSdk = 30
}

object AndroidX {
    private object Versions {
        const val coreKtx = "1.3.2"
        const val appCompat = "1.2.0"
        const val constraintLayout = "2.0.4"
        const val lifecycle = "2.2.0"
        const val cameraX = "1.0.0-beta12"
        const val cameraView = "1.0.0-alpha19"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val lifecycleViewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val camera2 = "androidx.camera:camera-camera2:${Versions.cameraX}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.cameraX}"
    const val cameraView = "androidx.camera:camera-view:${Versions.cameraView}"
}

object Libraries {
    private object Versions {
        const val material = "1.2.1"
        const val timber = "4.7.1"
        const val zxing = "3.3.0"
    }

    const val material = "com.google.android.material:material:${Versions.material}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val zxingCore = "com.google.zxing:core:${Versions.zxing}"
    const val zxingAndroidCore = "com.google.zxing:android-core:${Versions.zxing}"
}

object TestLibraries {
    private object Versions {
        const val junit = "4.13.1"
        const val androidTestJunit = "1.1.2"
        const val androidTestEspresso = "3.3.0"
    }

    const val junit = "junit:junit:${Versions.junit}"
    const val androidTestJunit = "androidx.test.ext:junit:${Versions.androidTestJunit}"
    const val androidTestEspresso =
        "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
}