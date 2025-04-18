package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout
import com.github.nandaarya.cleanarchitecturetemplate.source.configuration.*
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.entityKt
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.iRepositoryKt
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.useCaseInteractorKt
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.useCaseKt
import com.github.nandaarya.cleanarchitecturetemplate.source.ui.emptyActivityKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.daoKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.databaseKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.modelKt
import com.github.nandaarya.cleanarchitecturetemplate.source.myApplicationKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.apiConfigKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.apiServiceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.exampleResponseKt

fun RecipeExecutor.projectRecipe(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    useDomainLayer: Boolean,
) {
    val (projectData, srcOut) = moduleData
    val useAndroidX = projectData.androidXSupport

    val activityClass = "MainActivity"
    val layoutName = "activity_main"

    generateSimpleLayout(moduleData, "ui.$activityClass", layoutName, containerId = "main")

    if (useDomainLayer) {
        createDirectory(moduleData.srcDir.resolve("domain"))
        createDirectory(moduleData.srcDir.resolve("domain/model"))
        createDirectory(moduleData.srcDir.resolve("domain/repository"))
        createDirectory(moduleData.srcDir.resolve("domain/usecase"))

        val entityPath = srcOut.resolve("domain/model/ExampleEntity.kt")
        val entity = entityKt(packageName)
        save(entity, entityPath)

        val useCasePath = srcOut.resolve("domain/usecase/ExampleUseCase.kt")
        val useCase = useCaseKt(packageName)
        save(useCase, useCasePath)

        val useCaseInteractorPath = srcOut.resolve("domain/usecase/ExampleInteractor.kt")
        val useCaseInteractor = useCaseInteractorKt(packageName)
        save(useCaseInteractor, useCaseInteractorPath)

        val iRepositoryPath = srcOut.resolve("domain/repository/IExampleRepository.kt")
        val iRepository = iRepositoryKt(packageName)
        save(iRepository, iRepositoryPath)
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

    val buildGradleModulePath = moduleData.rootDir.resolve("build.gradle.kts")
    val buildGradleModuleGroovyPath = moduleData.rootDir.resolve("build.gradle")
    val buildGradleProjectPath = moduleData.projectTemplateData.rootDir.resolve("build.gradle.kts")
    val buildGradleProjectGroovyPath = moduleData.projectTemplateData.rootDir.resolve("build.gradle")
    val settingsGradlePath = moduleData.projectTemplateData.rootDir.resolve("settings.gradle.kts")
    val settingsGradleGroovyPath = moduleData.projectTemplateData.rootDir.resolve("settings.gradle")
    val buildGradleModule = buildGradleModuleKt(packageName)
    val buildGradleProject = buildGradleProjectKt()
    val settingsGradle = settingsGradleKt(moduleData.namespace.substringAfterLast("."))
    if (buildGradleModulePath.exists()) {
        buildGradleModulePath.delete()
        buildGradleProjectPath.delete()
        settingsGradlePath.delete()
    }
    if (buildGradleModuleGroovyPath.exists()) {
        buildGradleModuleGroovyPath.delete()
        buildGradleProjectGroovyPath.delete()
        settingsGradleGroovyPath.delete()
    }
    save(buildGradleModule, buildGradleModulePath)
    save(buildGradleProject, buildGradleProjectPath)
    save(settingsGradle, settingsGradlePath)

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

    val modelPath = srcOut.resolve("data/local/database/MyModel.kt")
    val model = modelKt(packageName)
    save(model, modelPath)

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

    val exampleResponsePath = srcOut.resolve("data/remote/response/ExampleResponse.kt")
    val exampleResponse = exampleResponseKt(packageName)
    save(exampleResponse, exampleResponsePath)

    val simpleActivityPath = srcOut.resolve("ui/MainActivity.kt")
    val simpleActivity =
        emptyActivityKt(packageName, moduleData.namespace, activityClass, layoutName, true, useAndroidX)
    save(simpleActivity, simpleActivityPath)
    open(simpleActivityPath)
}