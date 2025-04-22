package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout
import com.github.nandaarya.cleanarchitecturetemplate.source.configuration.*
import com.github.nandaarya.cleanarchitecturetemplate.source.data.di.repositoryModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.di.retrofitModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.di.roomModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.ui.emptyActivityKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.daoKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.databaseKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.localDataSourceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.modelKt
import com.github.nandaarya.cleanarchitecturetemplate.source.myApplicationKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.apiServiceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.exampleResponseKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.remoteDataSourceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.repositoryKt
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.*
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.di.useCaseModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.ui.mainViewModelKt

fun RecipeExecutor.projectRecipe(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    useDomainLayer: Boolean,
    useLocalDataSource: Boolean,
    useRemoteDataSource: Boolean,
    useRoom: Boolean,
    useRetrofit: Boolean
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
        createDirectory(moduleData.srcDir.resolve("domain/di"))

        val myModelEntityPath = srcOut.resolve("domain/model/MyModelEntity.kt")
        val myModelEntity = myModelEntityKt(packageName)
        save(myModelEntity, myModelEntityPath)

        val exampleResponseEntityPath = srcOut.resolve("domain/model/ExampleResponseEntity.kt")
        val exampleResponseEntity = exampleResponseEntityKt(packageName)
        save(exampleResponseEntity, exampleResponseEntityPath)

        val useCaseModulePath = srcOut.resolve("domain/di/UseCaseModule.kt")
        val useCaseModule = useCaseModuleKt(packageName)
        save(useCaseModule, useCaseModulePath)

        val useCasePath = srcOut.resolve("domain/usecase/ExampleUseCase.kt")
        val useCase = useCaseKt(packageName)
        save(useCase, useCasePath)

        val useCaseInteractorPath = srcOut.resolve("domain/usecase/ExampleInteractor.kt")
        val useCaseInteractor = useCaseInteractorKt(packageName)
        save(useCaseInteractor, useCaseInteractorPath)

        val iRepositoryPath = srcOut.resolve("domain/repository/IExampleRepository.kt")
        val iRepository = iRepositoryKt(packageName)
        save(iRepository, iRepositoryPath)

        val repositoryModulePath = srcOut.resolve("data/di/RepositoryModule.kt")
        val repositoryModule = repositoryModuleKt(
            packageName,
            useRoom && useLocalDataSource,
            useRetrofit && useRemoteDataSource
        )
        save(repositoryModule, repositoryModulePath)
    }

    createDirectory(moduleData.srcDir.resolve("ui"))
    createDirectory(moduleData.srcDir.resolve("data"))

    createDirectory(moduleData.srcDir.resolve("data/di"))
    createDirectory(moduleData.srcDir.resolve("ui"))

    // Override Configuration
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

    if (useLocalDataSource) {
        createDirectory(moduleData.srcDir.resolve("data/local"))
        createDirectory(moduleData.srcDir.resolve("data/local/room"))

        val modelPath = srcOut.resolve("data/local/room/MyModel.kt")
        val model = modelKt(packageName)
        save(model, modelPath)

        val daoPath = srcOut.resolve("data/local/room/Dao.kt")
        val dao = daoKt(packageName)
        save(dao, daoPath)

        val databasePath = srcOut.resolve("data/local/room/Database.kt")
        val database = databaseKt(packageName)
        save(database, databasePath)

        val localDataSourcePath = srcOut.resolve("data/local/LocalDataSource.kt")
        val localDataSource = localDataSourceKt(packageName, useDomainLayer)
        save(localDataSource, localDataSourcePath)

        val roomModulePath = srcOut.resolve("data/di/RoomModule.kt")
        val roomModule = roomModuleKt(packageName)
        save(roomModule, roomModulePath)
    }

    if (useRemoteDataSource) {
        createDirectory(moduleData.srcDir.resolve("data/remote"))
        createDirectory(moduleData.srcDir.resolve("data/remote/retrofit"))

        val remoteDataSourcePath = srcOut.resolve("data/remote/RemoteDataSource.kt")
        val remoteDataSource = remoteDataSourceKt(packageName, useDomainLayer)
        save(remoteDataSource, remoteDataSourcePath)

        val apiServicePath = srcOut.resolve("data/remote/retrofit/ApiService.kt")
        val apiService = apiServiceKt(packageName)
        save(apiService, apiServicePath)

        val exampleResponsePath = srcOut.resolve("data/remote/response/ExampleResponse.kt")
        val exampleResponse = exampleResponseKt(packageName)
        save(exampleResponse, exampleResponsePath)

        val retrofitModulePath = srcOut.resolve("data/di/RetrofitModule.kt")
        val retrofitModule = retrofitModuleKt(packageName)
        save(retrofitModule, retrofitModulePath)
    }

    val repositoryPath = srcOut.resolve("data/Repository.kt")
    val repository = repositoryKt(
        packageName,
        useDomainLayer,
        useLocalDataSource,
        useRemoteDataSource,
        useRoom && useLocalDataSource,
        useRetrofit && useRemoteDataSource)
    save(repository, repositoryPath)

    val mainViewModelPath = srcOut.resolve("ui/MainViewModel.kt")
    val mainViewModel = mainViewModelKt(
        packageName,
        useDomainLayer,
        useRoom && useLocalDataSource,
        useRetrofit && useRemoteDataSource)
    save(mainViewModel, mainViewModelPath)

    val mainActivityPath = srcOut.resolve("ui/MainActivity.kt")
    val mainActivity =
        emptyActivityKt(
            packageName,
            moduleData.namespace,
            activityClass, layoutName,
            true,
            useAndroidX,
            useDomainLayer,
            useRoom && useLocalDataSource,
            useRetrofit && useRemoteDataSource)
    save(mainActivity, mainActivityPath)
    open(mainActivityPath)
}