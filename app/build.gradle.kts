plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Android.compileSdk)

    defaultConfig {
        applicationId = "io.viewpoint.quarter"
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
    }
}

dependencies {
    implementation(Kotlin.stdLib)
    implementation(Kotlin.coroutinesCore)

    // androidx or google
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Libraries.material)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.lifecycleViewmodel)
    implementation(AndroidX.lifecycleLivedata)
    implementation(AndroidX.lifecycleRuntime)
    implementation(AndroidX.camera2)
    implementation(AndroidX.cameraLifecycle)
    implementation(AndroidX.cameraView)

    // images
    implementation(Libraries.zxingCore)
    implementation(Libraries.zxingAndroidCore)

    // logging
    implementation(Libraries.timber)

    testImplementation(TestLibraries.junit)

    androidTestImplementation(TestLibraries.androidTestJunit)
    androidTestImplementation(TestLibraries.androidTestEspresso)
}
