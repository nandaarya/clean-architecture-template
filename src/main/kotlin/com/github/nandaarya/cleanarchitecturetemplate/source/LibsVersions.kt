package com.github.nandaarya.cleanarchitecturetemplate.source

fun libsVersionsToml() = """
    [versions]
    agp = "8.8.0"
    kotlin = "2.1.0"
    coreKtx = "1.15.0"
    junit = "4.13.2"
    junitVersion = "1.2.1"
    espressoCore = "3.6.1"
    appcompat = "1.7.0"
    material = "1.12.0"
    activity = "1.10.0"
    androidxRoom = "2.6.1"
    hilt = "2.55"

    [libraries]
    androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
    junit = { group = "junit", name = "junit", version.ref = "junit" }
    androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
    androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
    androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
    material = { group = "com.google.android.material", name = "material", version.ref = "material" }
    androidx-activity = { group = "androidx.activity", name = "activity", version.ref = "activity" }
    androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "androidxRoom" }
    androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "androidxRoom" }
    androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "androidxRoom" }
    hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
    hilt-compiler = { module = "com.google.dagger:hilt-compiler", version.ref = "hilt" }

    [plugins]
    android-application = { id = "com.android.application", version.ref = "agp" }
    kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
""".trimIndent()