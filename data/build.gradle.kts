plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)

    // Kapt
    kotlin(Plugins.kapt)
}

android {
    namespace = Config.Data.namespace
    compileSdk = Config.targetAndCompileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetAndCompileSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {

    // Core
    implementation(Dependencies.Core.core)

    // Domain
    implementation(project(":domain"))

    // Lifecycle
    implementation(Dependencies.Lifecycle.liveData)
    implementation(Dependencies.Lifecycle.viewModel)
    implementation(Dependencies.Lifecycle.viewModelCompose)

    // Firebase auth
    implementation(Dependencies.Firebase.auth)

    // Firebase firestore
    implementation (Dependencies.Firebase.firestore)
}