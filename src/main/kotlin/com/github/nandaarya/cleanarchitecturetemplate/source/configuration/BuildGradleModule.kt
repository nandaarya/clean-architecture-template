package com.github.nandaarya.cleanarchitecturetemplate.source.configuration

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun buildGradleModuleKt(
    packageName: String,
    useRoom: Boolean,
    useRetrofit: Boolean
) = """
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)
}

android {
    namespace = "${escapeKotlinIdentifier(packageName)}"
    compileSdk = 35

    defaultConfig {
        applicationId = "${escapeKotlinIdentifier(packageName)}"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
        
    // View Model
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    
    ${if (useRoom) 
 """// room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)""" 
    else ""}
    
    ${if (useRetrofit) 
 """// Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)""" 
    else ""}
}
""".trimIndent()