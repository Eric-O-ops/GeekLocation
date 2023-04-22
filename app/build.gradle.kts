plugins {
    id(Plugins.application)
    id(Plugins.kotlinAndroid)

    // Kapt
    kotlin(Plugins.kapt)

    // Hilt
    id(Plugins.hilt)
    id(Plugins.mapsPlatform)
    id(Plugins.googleServices)
}

android {
    namespace = Config.App.namespace
    compileSdk = Config.targetAndCompileSdk

    defaultConfig {
        applicationId = Config.App.namespace
        minSdk = Config.minSdk
        targetSdk = Config.targetAndCompileSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    // JUnit
    testImplementation(Dependencies.Test.jUnit)
    androidTestImplementation(Dependencies.Test.extJUnit)

    // EspressoCore
    androidTestImplementation(Dependencies.Test.espressoCore)

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)

    // Domain
    implementation(project(":domain"))

    // Data
    implementation(project(":data"))

    // Presentation
    implementation(project(":presentation"))

    // PlayServicesMaps
    implementation(Dependencies.Maps.playServicesMaps)

    // Sign with google
    implementation (Dependencies.GoogleServices.googleAuthServies)

    // Firebase auth
    implementation(Dependencies.Firebase.auth)

    // Firebase firestore
    implementation (Dependencies.Firebase.firestore)
}