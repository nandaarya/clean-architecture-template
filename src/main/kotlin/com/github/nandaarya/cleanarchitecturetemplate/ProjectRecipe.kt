package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
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
import com.github.nandaarya.cleanarchitecturetemplate.source.readmeKt
import com.github.nandaarya.cleanarchitecturetemplate.source.ui.mainActivityLayoutKt
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
    generateConfigurationFiles(
        moduleData = moduleData,
        packageName = packageName,
        useLocalDataSource = useLocalDataSource,
        useRemoteDataSource = useRemoteDataSource,
        useRoom = useRoom,
        useRetrofit = useRetrofit
    )

    generateDomainLayerFiles(
        moduleData = moduleData,
        packageName = packageName,
        useDomainLayer = useDomainLayer,
        useLocalDataSource = useLocalDataSource,
        useRemoteDataSource = useRemoteDataSource,
        useRoom = useRoom,
        useRetrofit = useRetrofit
    )

    generateDataLayerFiles(
        moduleData = moduleData,
        packageName = packageName,
        useDomainLayer = useDomainLayer,
        useLocalDataSource = useLocalDataSource,
        useRemoteDataSource = useRemoteDataSource,
        useRoom = useRoom,
        useRetrofit = useRetrofit
    )

    generatePresentationLayerFiles(
        moduleData = moduleData,
        packageName = packageName,
        useDomainLayer = useDomainLayer,
        useLocalDataSource = useLocalDataSource,
        useRemoteDataSource = useRemoteDataSource,
        useRoom = useRoom,
        useRetrofit = useRetrofit
    )

    generateReadmeFile(moduleData)
}

fun RecipeExecutor.generateConfigurationFiles(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    useLocalDataSource: Boolean,
    useRemoteDataSource: Boolean,
    useRoom: Boolean,
    useRetrofit: Boolean
) {
    val srcOut = moduleData.srcDir

    val buildGradleModulePath = moduleData.rootDir.resolve("build.gradle.kts")
    val buildGradleModuleGroovyPath = moduleData.rootDir.resolve("build.gradle")
    val buildGradleProjectPath = moduleData.projectTemplateData.rootDir.resolve("build.gradle.kts")
    val buildGradleProjectGroovyPath = moduleData.projectTemplateData.rootDir.resolve("build.gradle")
    val settingsGradlePath = moduleData.projectTemplateData.rootDir.resolve("settings.gradle.kts")
    val settingsGradleGroovyPath = moduleData.projectTemplateData.rootDir.resolve("settings.gradle")
    val buildGradleModule = buildGradleModuleKt(
        packageName,
        useRoom && useLocalDataSource,
        useRetrofit && useRemoteDataSource
    )
    val buildGradleProject = buildGradleProjectKt()
    val settingsGradle = settingsGradleKt(packageName.substringAfterLast("."))
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
    val libsVersions = libsVersionsToml(
        useRoom && useLocalDataSource,
        useRetrofit && useRemoteDataSource
    )
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
}

fun RecipeExecutor.generateDomainLayerFiles(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    useDomainLayer: Boolean,
    useLocalDataSource: Boolean,
    useRemoteDataSource: Boolean,
    useRoom: Boolean,
    useRetrofit: Boolean
) {
    val srcOut = moduleData.srcDir

    if (useDomainLayer) {
        createDirectory(moduleData.srcDir.resolve("domain"))
        createDirectory(moduleData.srcDir.resolve("domain/model"))
        createDirectory(moduleData.srcDir.resolve("domain/repository"))
        createDirectory(moduleData.srcDir.resolve("domain/usecase"))
        createDirectory(moduleData.srcDir.resolve("domain/di"))

        if (useRoom && useLocalDataSource) {
            val myModelEntityPath = srcOut.resolve("domain/model/MyModelEntity.kt")
            val myModelEntity = myModelEntityKt(packageName)
            save(myModelEntity, myModelEntityPath)
        }

        if (useRetrofit && useRemoteDataSource) {
            val exampleResponseEntityPath = srcOut.resolve("domain/model/ExampleResponseEntity.kt")
            val exampleResponseEntity = exampleResponseEntityKt(packageName)
            save(exampleResponseEntity, exampleResponseEntityPath)
        }

        val useCaseModulePath = srcOut.resolve("domain/di/UseCaseModule.kt")
        val useCaseModule = useCaseModuleKt(packageName)
        save(useCaseModule, useCaseModulePath)

        val useCasePath = srcOut.resolve("domain/usecase/ExampleUseCase.kt")
        val useCase = useCaseKt(
            packageName,
            useRoom && useLocalDataSource,
            useRetrofit && useRemoteDataSource
        )
        save(useCase, useCasePath)

        val useCaseInteractorPath = srcOut.resolve("domain/usecase/ExampleInteractor.kt")
        val useCaseInteractor = useCaseInteractorKt(
            packageName,
            useRoom && useLocalDataSource,
            useRetrofit && useRemoteDataSource
        )
        save(useCaseInteractor, useCaseInteractorPath)

        val iRepositoryPath = srcOut.resolve("domain/repository/IExampleRepository.kt")
        val iRepository = iRepositoryKt(
            packageName,
            useRoom && useLocalDataSource,
            useRetrofit && useRemoteDataSource
        )
        save(iRepository, iRepositoryPath)

        val repositoryModulePath = srcOut.resolve("data/di/RepositoryModule.kt")
        val repositoryModule = repositoryModuleKt(
            packageName,
            useRoom && useLocalDataSource,
            useRetrofit && useRemoteDataSource
        )
        save(repositoryModule, repositoryModulePath)
    }
}

fun RecipeExecutor.generateDataLayerFiles(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    useDomainLayer: Boolean,
    useLocalDataSource: Boolean,
    useRemoteDataSource: Boolean,
    useRoom: Boolean,
    useRetrofit: Boolean
) {
    val srcOut = moduleData.srcDir

    createDirectory(moduleData.srcDir.resolve("data"))
    createDirectory(moduleData.srcDir.resolve("data/di"))

    if (useLocalDataSource) {

        val localDataSourcePath = srcOut.resolve("data/local/LocalDataSource.kt")
        val localDataSource = localDataSourceKt(packageName, useDomainLayer, useRoom)
        save(localDataSource, localDataSourcePath)

        if (useRoom) {
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

            val roomModulePath = srcOut.resolve("data/di/RoomModule.kt")
            val roomModule = roomModuleKt(packageName)
            save(roomModule, roomModulePath)
        }
    }

    if (useRemoteDataSource) {

        val remoteDataSourcePath = srcOut.resolve("data/remote/RemoteDataSource.kt")
        val remoteDataSource = remoteDataSourceKt(packageName, useDomainLayer, useRetrofit)
        save(remoteDataSource, remoteDataSourcePath)

        if (useRetrofit) {
            createDirectory(moduleData.srcDir.resolve("data/remote"))
            createDirectory(moduleData.srcDir.resolve("data/remote/retrofit"))

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
    }

    val repositoryPath = srcOut.resolve("data/Repository.kt")
    val repository = repositoryKt(
        packageName,
        useDomainLayer,
        useLocalDataSource,
        useRemoteDataSource,
        useRoom && useLocalDataSource,
        useRetrofit && useRemoteDataSource
    )
    save(repository, repositoryPath)
}

fun RecipeExecutor.generatePresentationLayerFiles(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    useDomainLayer: Boolean,
    useLocalDataSource: Boolean,
    useRemoteDataSource: Boolean,
    useRoom: Boolean,
    useRetrofit: Boolean
) {
    val srcOut = moduleData.srcDir
    val useAndroidX = moduleData.projectTemplateData.androidXSupport

    createDirectory(moduleData.srcDir.resolve("ui"))

    val mainViewModelPath = srcOut.resolve("ui/MainViewModel.kt")
    val mainViewModel = mainViewModelKt(
        packageName,
        useDomainLayer,
        useRoom && useLocalDataSource,
        useRetrofit && useRemoteDataSource
    )
    save(mainViewModel, mainViewModelPath)

    val mainActivityLayoutPath = moduleData.resDir.resolve("layout/activity_main.xml")
    val mainActivityLayout = mainActivityLayoutKt()
    save(mainActivityLayout, mainActivityLayoutPath)

    val mainActivityPath = srcOut.resolve("ui/MainActivity.kt")
    val mainActivity =
        emptyActivityKt(
            packageName,
            true,
            useAndroidX,
            useDomainLayer,
            useRoom && useLocalDataSource,
            useRetrofit && useRemoteDataSource
        )
    save(mainActivity, mainActivityPath)
}

fun RecipeExecutor.generateReadmeFile(moduleData: ModuleTemplateData) {
    val readmePath = moduleData.srcDir.resolve("README.md")
    val readme = readmeKt()
    save(readme, readmePath)
    open(readmePath)
}