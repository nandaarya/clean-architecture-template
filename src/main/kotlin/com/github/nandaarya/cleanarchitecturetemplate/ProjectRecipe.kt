package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout
import com.android.tools.idea.wizard.template.impl.activities.emptyActivity.src.emptyActivityJava
import com.github.nandaarya.cleanarchitecturetemplate.source.*
import com.github.nandaarya.cleanarchitecturetemplate.source.configuration.androidManifestXml
import com.github.nandaarya.cleanarchitecturetemplate.source.configuration.buildGradleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.configuration.libsVersionsToml
import com.github.nandaarya.cleanarchitecturetemplate.source.local.*
import com.github.nandaarya.cleanarchitecturetemplate.source.remote.apiConfigKt
import com.github.nandaarya.cleanarchitecturetemplate.source.remote.apiServiceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.remote.registerResponseKt

fun RecipeExecutor.projectRecipe(
    moduleData: ModuleTemplateData,
    activityClass: String,
    generateLayout: Boolean,
    layoutName: String,
    isLauncher: Boolean,
    packageName: PackageName
) {
    val (projectData, srcOut) = moduleData
    val useAndroidX = projectData.androidXSupport
    val ktOrJavaExt = projectData.language.extension

    applyPlugin("com.google.devtools.ksp", "2.1.0-1.0.29")
    applyPlugin("com.google.dagger.hilt.android", "2.55")

    if (generateLayout) {
        generateSimpleLayout(moduleData, "ui.$activityClass", layoutName, containerId = "main")
    }

    createDirectory(moduleData.srcDir.resolve("ui"))
    createDirectory(moduleData.srcDir.resolve("data"))

    createDirectory(moduleData.srcDir.resolve("data/di"))
    createDirectory(moduleData.srcDir.resolve("data/local/di"))
    createDirectory(moduleData.srcDir.resolve("data/local"))
    createDirectory(moduleData.srcDir.resolve("data/local/database"))
    createDirectory(moduleData.srcDir.resolve("data/remote"))
    createDirectory(moduleData.srcDir.resolve("data/remote/retrofit"))
    createDirectory(moduleData.srcDir.resolve("ui"))

    val buildGradlePath = moduleData.rootDir.resolve("build.gradle.kts")
    val buildGradle = buildGradleKt(packageName)
    if (buildGradlePath.exists()) {
        buildGradlePath.delete()
    }
    save(buildGradle, buildGradlePath)

    val libsVersionsPath = moduleData.projectTemplateData.rootDir.resolve("gradle/libs.versions.toml")
    val libsVersions = libsVersionsToml()
    if (libsVersionsPath.exists()) {
        libsVersionsPath.delete()
    }
    save(libsVersions, libsVersionsPath)

    val androidManifestPath = moduleData.manifestDir.resolve("AndroidManifest.xml")
    val androidManifest = androidManifestXml()
    mergeXml(androidManifest, androidManifestPath)

    val myApplicationPath = srcOut.resolve("MyApplication.kt")
    val myApplication = myApplicationKt(packageName)
    save(myApplication, myApplicationPath)

    val entityPath = srcOut.resolve("data/local/database/MyModel.kt")
    val entity = entityKt(packageName)
    save(entity, entityPath)

    val daoPath = srcOut.resolve("data/local/database/Dao.kt")
    val dao = daoKt(packageName)
    save(dao, daoPath)

    val databasePath = srcOut.resolve("data/local/database/Database.kt")
    val database = databaseKt(packageName)
    save(database, databasePath)

    val apiConfigPath = srcOut.resolve("data/remote/retrofit/ApiConfig.kt")
    val apiConfig = apiConfigKt(packageName)
    save(apiConfig, apiConfigPath)

    val apiServicePath = srcOut.resolve("data/remote/retrofit/ApiService.kt")
    val apiService = apiServiceKt(packageName)
    save(apiService, apiServicePath)

    val registerResponsePath = srcOut.resolve("data/remote/response/RegisterResponse.kt")
    val registerResponse = registerResponseKt(packageName)
    save(registerResponse, registerResponsePath)

    val simpleActivity = when (projectData.language) {
        Language.Kotlin -> emptyActivityKt(packageName, moduleData.namespace, activityClass, layoutName, generateLayout, useAndroidX)
        Language.Java -> emptyActivityJava(packageName, moduleData.namespace, activityClass, layoutName, generateLayout, useAndroidX)
    }
    val simpleActivityPath = srcOut.resolve("ui/$activityClass.$ktOrJavaExt")
    save(simpleActivity, simpleActivityPath)
    open(simpleActivityPath)
}