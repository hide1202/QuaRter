object Kotlin {
    const val version = "1.4.10"

    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
}

object CommonVersions {
    const val hilt = "2.29.1-alpha"
}

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "7.0.0-alpha01"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val hiltGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${CommonVersions.hilt}"
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
        const val hilt = "1.0.0-alpha02"
        const val paging = "3.0.0-alpha09"
    }

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val lifecycleViewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val hiltLifecycle = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt}"
    const val hiltKapt = "androidx.hilt:hilt-compiler:${Versions.hilt}"

    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
}

object Libraries {
    private object Versions {
        const val material = "1.2.1"
        const val glide = "4.11.0"
        const val moshi = "1.11.0"
        const val okhttp = "4.9.0"
        const val retrofit = "2.9.0"
        const val timber = "4.7.1"
        const val shimmer = "0.5.0"
        const val dagger = "2.29.1"
    }

    const val material = "com.google.android.material:material:${Versions.material}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_kapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val hilt = "com.google.dagger:hilt-android:${CommonVersions.hilt}"
    const val hilt_kapt = "com.google.dagger:hilt-android-compiler:${CommonVersions.hilt}"
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