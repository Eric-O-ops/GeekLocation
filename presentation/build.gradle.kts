plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)

    // Kapt
    kotlin(Plugins.kapt)

    // Hilt
    id(Plugins.hilt)

    // Google
    id(Plugins.googleServices)

    // Map
    id(Plugins.mapsPlatform)
}

android {
    namespace = Config.Presentation.namespace
    compileSdk = Config.targetAndCompileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetAndCompileSdk

        testInstrumentationRunner = Config.testInstrumentationRunner
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

    @Suppress("UnstableApiUsage")
    buildFeatures.viewBinding = true

}

dependencies {

    // Core
    implementation(Dependencies.Core.core)

    // AppCompat
    implementation(Dependencies.UIComponents.appCompat)

    // Material
    implementation(Dependencies.UIComponents.material)

    // ConstraintLayout
    implementation(Dependencies.UIComponents.constraintLayout)

    // NavComponents
    implementation(Dependencies.NavComponents.navigationUI)
    implementation(Dependencies.NavComponents.navigationFragment)

    // Lifecycle
    implementation(Dependencies.Lifecycle.liveData)
    implementation(Dependencies.Lifecycle.viewModel)
    implementation(Dependencies.Lifecycle.viewModelCompose)

    // ViewBindingPropertyDelegate
    implementation(Dependencies.ViewBindingPropertyDelegate.viewBindingPropertyDelegate)

    // Domain
    implementation(project(":domain"))

    // Hilt
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.compiler)

    // Sign with google
    implementation(Dependencies.GoogleServices.googleAuthServies)

    // Firebase auth
    implementation(Dependencies.Firebase.auth)

    // Firebase firestore
    implementation(Dependencies.Firebase.firestore)

    // Google map
    implementation(Dependencies.Maps.playServicesMaps)
    implementation(Dependencies.Maps.serviceLocation)

    // JUnit
    testImplementation(Dependencies.Test.jUnit)
    androidTestImplementation(Dependencies.Test.extJUnit)

    // EspressoCore
    androidTestImplementation(Dependencies.Test.espressoCore)

}