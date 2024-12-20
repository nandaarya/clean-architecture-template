package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.RecipeExecutor

fun RecipeExecutor.addRoomDependencies(roomVersion: String = "2.6.1") {
    addDependency(mavenCoordinate = "androidx.room:room-compiler:$roomVersion", configuration = "ksp")
    addDependency(mavenCoordinate = "androidx.room:room-runtime:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-ktx:$roomVersion")
}