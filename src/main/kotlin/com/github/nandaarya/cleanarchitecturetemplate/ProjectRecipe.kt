package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addMaterial3Dependency
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout
import com.android.tools.idea.wizard.template.impl.activities.emptyActivity.src.emptyActivityJava
import com.github.nandaarya.cleanarchitecturetemplate.source.*

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

    applyPlugin("com.google.devtools.ksp", "1.9.10-1.0.13")
    addMaterial3Dependency()

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

    val androidManifestPath = moduleData.manifestDir.resolve("AndroidManifest.xml")
    val androidManifest = androidManifestKt()
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

    val simpleActivity = when (projectData.language) {
        Language.Kotlin -> emptyActivityKt(packageName, moduleData.namespace, activityClass, layoutName, generateLayout, useAndroidX)
        Language.Java -> emptyActivityJava(packageName, moduleData.namespace, activityClass, layoutName, generateLayout, useAndroidX)
    }
    val simpleActivityPath = srcOut.resolve("ui/$activityClass.$ktOrJavaExt")
    save(simpleActivity, simpleActivityPath)
    open(simpleActivityPath)
}