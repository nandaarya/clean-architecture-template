package com.github.nandaarya.cleanarchitecturetemplate.source.configuration

fun libsVersionsToml(
    useRoom: Boolean,
    useRetrofit: Boolean
) = """
[versions]
agp = "8.6.1"
kotlin = "2.1.20"
coreKtx = "1.16.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
appcompat = "1.7.0"
constraintlayout = "2.2.1"
googleDevtoolsKsp = "2.1.20-2.0.0"
googleDaggerHiltAndroid = "2.56.1"
lifecycleViewmodelKtx = "2.8.7"
material = "1.12.0"
activity = "1.10.1"
hilt = "2.55"
${if (useRoom) """androidxRoom = "2.7.0"""" else ""}
${if (useRetrofit) """retrofit = "2.11.0"""" else ""}

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-viewmodel-ktx = { module = "androidx.lifecycle:lifecycle-viewmodel-ktx", version.ref = "lifecycleViewmodelKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-constraintlayout = { group = "androidx.constraintlayout", name = "constraintlayout", version.ref = "constraintlayout" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
material = { group = "com.google.android.material", name = "material", version.ref = "material" }
androidx-activity = { group = "androidx.activity", name = "activity", version.ref = "activity" }
hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
hilt-compiler = { module = "com.google.dagger:hilt-compiler", version.ref = "hilt" }
${if (useRetrofit) """
    retrofit = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
    converter-gson = { module = "com.squareup.retrofit2:converter-gson", version.ref = "retrofit" }
""".trimIndent() else ""}
${if (useRoom) """
    androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "androidxRoom" }
    androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "androidxRoom" }
    androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "androidxRoom" }
""".trimIndent() else ""}

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
google-devtools-ksp = { id = "com.google.devtools.ksp", version.ref = "googleDevtoolsKsp" }
google-dagger-hilt-android = { id = "com.google.dagger.hilt.android", version.ref = "googleDaggerHiltAndroid" }
""".trimIndent()