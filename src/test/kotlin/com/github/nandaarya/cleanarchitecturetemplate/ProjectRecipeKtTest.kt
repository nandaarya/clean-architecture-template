package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.ProjectTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.github.nandaarya.cleanarchitecturetemplate.source.configuration.*
import com.github.nandaarya.cleanarchitecturetemplate.source.data.di.repositoryModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.di.retrofitModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.di.roomModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.daoKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.databaseKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.localDataSourceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.local.modelKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.apiServiceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.exampleResponseKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.remote.remoteDataSourceKt
import com.github.nandaarya.cleanarchitecturetemplate.source.data.repositoryKt
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.*
import com.github.nandaarya.cleanarchitecturetemplate.source.domain.di.useCaseModuleKt
import com.github.nandaarya.cleanarchitecturetemplate.source.myApplicationKt
import com.github.nandaarya.cleanarchitecturetemplate.source.readmeKt
import com.github.nandaarya.cleanarchitecturetemplate.source.ui.emptyActivityKt
import com.github.nandaarya.cleanarchitecturetemplate.source.ui.mainActivityLayoutKt
import com.github.nandaarya.cleanarchitecturetemplate.source.ui.mainViewModelKt
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import java.io.File

@MockitoSettings(strictness = Strictness.LENIENT)
class ProjectRecipeKtTest {

    private lateinit var moduleData: ModuleTemplateData
    private lateinit var recipeExecutor: RecipeExecutor
    private lateinit var packageName: PackageName

    @BeforeEach
    fun setUp() {
        // Using mock versions of the data classes
        moduleData = mock(ModuleTemplateData::class.java)
        recipeExecutor = mock(RecipeExecutor::class.java)
        val mockProjectTemplateData = mock(ProjectTemplateData::class.java)
        packageName = "com.example.unittest"

        // Mocking ModuleTemplateData and setting its behavior
        moduleData = mock(ModuleTemplateData::class.java).apply {
            `when`(srcDir).thenReturn(File("src"))
            `when`(resDir).thenReturn(File("res"))
            `when`(manifestDir).thenReturn(File("manifest"))
            `when`(rootDir).thenReturn(File("root"))

            // Mock the projectTemplateData to return the mock ProjectTemplateData
            `when`(projectTemplateData).thenReturn(mockProjectTemplateData)
        }

        // Mocking behavior for ProjectTemplateData's rootDir
        `when`(mockProjectTemplateData.rootDir).thenReturn(File("root"))
    }

    @Test
    fun projectRecipeTest() {
        // Call the method
        recipeExecutor.projectRecipe(
            moduleData = moduleData,
            packageName = packageName,
            useDomainLayer = true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )

        // Verify that the appropriate methods were called
        verify(recipeExecutor).generateConfigurationFiles(
            moduleData, packageName,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )
        verify(recipeExecutor).generateDomainLayerFiles(
            moduleData, packageName,
            useDomainLayer = true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )
        verify(recipeExecutor).generateDataLayerFiles(
            moduleData, packageName,
            useDomainLayer = true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )
        verify(recipeExecutor).generatePresentationLayerFiles(
            moduleData, packageName, true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )
        verify(recipeExecutor).generateReadmeFile(moduleData)
    }

    @Test
    fun generateConfigurationFilesTest() {
        // Call the method
        recipeExecutor.generateConfigurationFiles(
            moduleData, packageName,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )

        val srcOut = moduleData.srcDir

        // Mock the File objects
        val buildGradleModulePath = mock(File::class.java)
        val buildGradleProjectPath = mock(File::class.java)
        val settingsGradlePath = mock(File::class.java)
        val buildGradleModuleGroovyPath = mock(File::class.java)
        val buildGradleProjectGroovyPath = mock(File::class.java)
        val settingsGradleGroovyPath = mock(File::class.java)

        // Stub the `exists()` method to return true (files exist)
        `when`(buildGradleModulePath.exists()).thenReturn(true)
        `when`(buildGradleProjectPath.exists()).thenReturn(true)
        `when`(settingsGradlePath.exists()).thenReturn(true)
        `when`(buildGradleModuleGroovyPath.exists()).thenReturn(true)
        `when`(buildGradleProjectGroovyPath.exists()).thenReturn(true)
        `when`(settingsGradleGroovyPath.exists()).thenReturn(true)

        // Stub the `delete()` method to do nothing (simulating deletion)
        `when`(buildGradleModulePath.delete()).thenReturn(true)
        `when`(buildGradleProjectPath.delete()).thenReturn(true)
        `when`(settingsGradlePath.delete()).thenReturn(true)
        `when`(buildGradleModuleGroovyPath.delete()).thenReturn(true)
        `when`(buildGradleProjectGroovyPath.delete()).thenReturn(true)
        `when`(settingsGradleGroovyPath.delete()).thenReturn(true)

        // Code under test: delete files if they exist
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

        // Verify that delete() was called on the mock file objects
        verify(buildGradleModulePath).delete()
        verify(buildGradleProjectPath).delete()
        verify(settingsGradlePath).delete()
        verify(buildGradleModuleGroovyPath).delete()
        verify(buildGradleProjectGroovyPath).delete()
        verify(settingsGradleGroovyPath).delete()

        verify(recipeExecutor).save(
            buildGradleModuleKt(packageName, true, useRetrofit = true),
            moduleData.rootDir.resolve("build.gradle.kts")
        )
        verify(recipeExecutor).save(
            buildGradleProjectKt(),
            moduleData.projectTemplateData.rootDir.resolve("build.gradle.kts")
        )
        verify(recipeExecutor).save(
            settingsGradleKt(packageName.substringAfterLast(".")),
            moduleData.projectTemplateData.rootDir.resolve("settings.gradle.kts")
        )
        verify(recipeExecutor).save(
            libsVersionsToml(
                useRoom = true, useRetrofit = true
            ), moduleData.projectTemplateData.rootDir.resolve("gradle/libs.versions.toml")
        )
        verify(recipeExecutor).mergeXml(androidManifestXml(), moduleData.manifestDir.resolve("AndroidManifest.xml"))
        verify(recipeExecutor).save(myApplicationKt(packageName), srcOut.resolve("MyApplication.kt"))
    }

    @Test
    fun generateDomainLayerFilesTest() {
        // Call the method
        recipeExecutor.generateDomainLayerFiles(
            moduleData, packageName,
            useDomainLayer = true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )

        val srcOut = moduleData.srcDir

        // Verify that the directories are being created
        verify(recipeExecutor).createDirectory(srcOut.resolve("domain"))
        verify(recipeExecutor).createDirectory(srcOut.resolve("domain/model"))
        verify(recipeExecutor).createDirectory(srcOut.resolve("domain/repository"))
        verify(recipeExecutor).createDirectory(srcOut.resolve("domain/usecase"))
        verify(recipeExecutor).createDirectory(srcOut.resolve("domain/di"))

        // Verify that files are being saved for "Room" and "Retrofit" enabled
        verify(recipeExecutor).save(
            myModelEntityKt(packageName),
            srcOut.resolve("domain/model/MyModelEntity.kt")
        )
        verify(recipeExecutor).save(
            exampleResponseEntityKt(packageName),
            srcOut.resolve("domain/model/ExampleResponseEntity.kt")
        )

        // Verify that other domain-related files are being saved
        verify(recipeExecutor).save(
            useCaseModuleKt(packageName),
            srcOut.resolve("domain/di/UseCaseModule.kt")
        )
        verify(recipeExecutor).save(
            useCaseKt(packageName, true, useRetrofit = true),
            srcOut.resolve("domain/usecase/ExampleUseCase.kt")
        )
        verify(recipeExecutor).save(
            useCaseInteractorKt(packageName, useRoom = true, useRetrofit = true),
            srcOut.resolve("domain/usecase/ExampleInteractor.kt")
        )
        verify(recipeExecutor).save(
            iRepositoryKt(packageName, useRoom = true, useRetrofit = true),
            srcOut.resolve("domain/repository/IExampleRepository.kt")
        )
        verify(recipeExecutor).save(
            repositoryModuleKt(packageName, useRoom = true, useRetrofit = true),
            srcOut.resolve("data/di/RepositoryModule.kt")
        )
    }

    @Test
    fun generateDataLayerFilesTest() {
        // Call the method
        recipeExecutor.generateDataLayerFiles(
            moduleData, packageName,
            useDomainLayer = true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )

        val srcOut = moduleData.srcDir

        // Verify that the main data directories are being created
        verify(recipeExecutor).createDirectory(srcOut.resolve("data"))
        verify(recipeExecutor).createDirectory(srcOut.resolve("data/di"))

        // Verify that the local data source file is created
        verify(recipeExecutor).save(
            localDataSourceKt(packageName, true, useRoom = true),
            srcOut.resolve("data/local/LocalDataSource.kt")
        )

        // Verify that the room-related directories are being created
        verify(recipeExecutor).createDirectory(srcOut.resolve("data/local"))
        verify(recipeExecutor).createDirectory(srcOut.resolve("data/local/room"))

        // Verify that the room model file is being created
        verify(recipeExecutor).save(
            modelKt(packageName),
            srcOut.resolve("data/local/room/MyModel.kt")
        )

        // Verify that the DAO file is being created
        verify(recipeExecutor).save(
            daoKt(packageName),
            srcOut.resolve("data/local/room/Dao.kt")
        )

        // Verify that the database file is being created
        verify(recipeExecutor).save(
            databaseKt(packageName),
            srcOut.resolve("data/local/room/Database.kt")
        )

        // Verify that the room module file is being created
        verify(recipeExecutor).save(
            roomModuleKt(packageName),
            srcOut.resolve("data/di/RoomModule.kt")
        )

        // Verify that the remote data source file is created
        verify(recipeExecutor).save(
            remoteDataSourceKt(packageName, useDomainLayer = true, useRetrofit = true),
            srcOut.resolve("data/remote/RemoteDataSource.kt")
        )

        // Verify that the remote retrofit-related directories are being created
        verify(recipeExecutor).createDirectory(srcOut.resolve("data/remote"))
        verify(recipeExecutor).createDirectory(srcOut.resolve("data/remote/retrofit"))

        // Verify that the API service file is being created
        verify(recipeExecutor).save(
            apiServiceKt(packageName),
            srcOut.resolve("data/remote/retrofit/ApiService.kt")
        )

        // Verify that the example response file is being created
        verify(recipeExecutor).save(
            exampleResponseKt(packageName),
            srcOut.resolve("data/remote/response/ExampleResponse.kt")
        )

        // Verify that the retrofit module file is being created
        verify(recipeExecutor).save(
            retrofitModuleKt(packageName),
            srcOut.resolve("data/di/RetrofitModule.kt")
        )

        // Verify that the repository file is created
        verify(recipeExecutor).save(
            repositoryKt(
                packageName,
                useDomainLayer = true,
                useLocalDataSource = true,
                useRemoteDataSource = true,
                useRoom = true,
                useRetrofit = true
            ),
            srcOut.resolve("data/Repository.kt")
        )
    }

    @Test
    fun generatePresentationLayerFilesTest() {
        // Call the method
        recipeExecutor.generatePresentationLayerFiles(
            moduleData, packageName,
            useDomainLayer = true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )

        val srcOut = moduleData.srcDir
        val resOut = moduleData.resDir
        val useAndroidX = moduleData.projectTemplateData.androidXSupport

        // Verify that the main UI directory is being created
        verify(recipeExecutor).createDirectory(srcOut.resolve("ui"))

        // Verify that the MainViewModel file is being saved
        verify(recipeExecutor).save(
            mainViewModelKt(
                packageName,
                useDomainLayer = true,
                useRoom = true,
                useRetrofit = true
            ),
            srcOut.resolve("ui/MainViewModel.kt")
        )

        // Verify that the main activity layout file is being saved
        verify(recipeExecutor).save(
            mainActivityLayoutKt(),
            resOut.resolve("layout/activity_main.xml")
        )

        // Verify that the MainActivity file is being saved
        verify(recipeExecutor).save(
            emptyActivityKt(
                packageName,
                true,
                useAndroidX,
                useDomainLayer = true,
                useRoom = true,
                useRetrofit = true
            ),
            srcOut.resolve("ui/MainActivity.kt")
        )
    }

    @Test
    fun generateReadmeFileTest() {
        // Call the method
        recipeExecutor.generateReadmeFile(moduleData)

        // Verify that the README file is generated
        verify(recipeExecutor).save(readmeKt(), moduleData.srcDir.resolve("README.md"))
    }
}