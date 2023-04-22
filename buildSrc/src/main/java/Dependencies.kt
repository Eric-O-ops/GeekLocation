object Version {
    const val applicationAndKotlinAndroid = "7.4.0"
    const val kotlinAndroid = "1.8.0"
    const val lifecycle = "2.5.1"
    const val navigation = "2.5.2"
    //hilt
    const val hilt = "2.44"
    const val hiltKtx = "1.9.0"
    //firebase
    const val firebaseAuth = "21.1.0"
    const val firestore = "24.4.2"
    const val googleAuthServices = "20.4.1"
    //maps
    const val mapsPlatform = "2.0.1"
    const val googleServices = "4.3.15"
}

object Plugins {
    const val application = "com.android.application"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val hilt = "com.google.dagger.hilt.android"
    const val kapt = "kapt"
    const val mapsPlatform = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    const val androidLibrary = "com.android.library"
    const val kotlinJVM = "org.jetbrains.kotlin.jvm"
    const val javaLibrary = "java-library"
    const val googleServices = "com.google.gms.google-services"
}

object Dependencies {

    object Core {
        const val core = "androidx.core:core-ktx:1.9.0"
    }

    object UIComponents {
        const val appCompat = "androidx.appcompat:appcompat:1.5.1"
        const val material = "com.google.android.material:material:1.8.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    }

    object ViewBindingPropertyDelegate {
        const val viewBindingPropertyDelegate =
            "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6"
    }

    object Lifecycle {
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycle}"
        const val viewModelCompose =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.lifecycle}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle}"
    }

    object Hilt {
        const val hiltKtx = "androidx.core:core-ktx:${Version.hiltKtx}"
        const val hilt = "com.google.dagger:hilt-android:${Version.hilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
    }

    object NavComponents {
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
    }

    object Coroutines {
        const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
        const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
        const val coroutineCoreJVM = "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4"
    }

    object Inject {
        const val inject = "javax.inject:javax.inject:1"
    }

    object Test {
        const val jUnit = "junit:junit:4.13.2"
        const val extJUnit = "androidx.test.ext:junit:1.1.3"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Maps {
        const val playServicesMaps = "com.google.android.gms:play-services-maps:18.1.0"
        const val serviceLocation = "com.google.android.gms:play-services-location:21.0.1"
    }

    object GoogleServices {
        const val googleAuthServies =
            "com.google.android.gms:play-services-auth:${Version.googleAuthServices}"
    }

    object Firebase {
        const val auth = "com.google.firebase:firebase-auth-ktx:${Version.firebaseAuth}"
        const val firestore = "com.google.firebase:firebase-firestore-ktx:${Version.firestore}"
    }
}