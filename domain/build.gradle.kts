plugins {
    id (Plugins.javaLibrary)
    id (Plugins.kotlinJVM)
}

java {

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    // Inject
    api(Dependencies.Inject.inject)

    // Coroutines
    api(Dependencies.Coroutines.coroutineCore)
    api(Dependencies.Coroutines.coroutineCoreJVM)
    api(Dependencies.Coroutines.coroutineAndroid)
}