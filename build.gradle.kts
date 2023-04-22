plugins {
    id(Plugins.application) version Version.applicationAndKotlinAndroid apply false
    id(Plugins.androidLibrary) version Version.applicationAndKotlinAndroid apply false
    id(Plugins.kotlinAndroid) version Version.kotlinAndroid apply false
    id(Plugins.kotlinJVM) version Version.kotlinAndroid apply false

    // Hilt
    id(Plugins.hilt) version Version.hilt apply false

    // MapsPlatform
    id(Plugins.mapsPlatform) version Version.mapsPlatform apply false

    // Google services
    id(Plugins.googleServices) version Version.googleServices apply false
}