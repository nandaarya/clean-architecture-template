package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.File

class ProjectTemplateTest {

    private lateinit var template: Template

    private lateinit var useDomainLayer: BooleanParameter
    private lateinit var useRemoteDataSource: BooleanParameter
    private lateinit var useLocalDataSource: BooleanParameter
    private lateinit var useRoom: BooleanParameter
    private lateinit var useRetrofit: BooleanParameter

    @BeforeEach
    fun setUp() {
        template = projectTemplate

        @Suppress("DEPRECATION")
        MockitoAnnotations.initMocks(this)

        useDomainLayer = booleanParameter {
            name = "Domain Layer (Use Case, Entity, etc)"
            default = true
            help = "If true, Domain Layer file will be generated"
        }

        useRemoteDataSource = booleanParameter {
            name = "RemoteDataSource"
            default = true
            help = "If true, RemoteDataSource file will be generated"
        }

        useLocalDataSource = booleanParameter {
            name = "LocalDataSource"
            default = true
            help = "If true, LocalDataSource file will be generated"
        }

        useRoom = booleanParameter {
            name = "Room"
            default = true
            help = "If true, Room file will be generated"
            enabled = { useLocalDataSource.value }
        }

        useRetrofit = booleanParameter {
            name = "Retrofit"
            default = true
            help = "If true, Retrofit file will be generated"
            enabled = { useRemoteDataSource.value }
        }

    }

    @Test
    fun `test template has correct basic properties`() {
        assertEquals("Clean Architecture Project", projectTemplate.name)
        assertEquals(com.android.tools.idea.wizard.template.impl.activities.common.MIN_API, projectTemplate.minSdk)
        assertEquals(
            "Creates a new project template with clean architecture. It will always use Kotlin as the programming language, Kotlin DSL for build configuration language, and Hilt as the Dependency Injection.",
            projectTemplate.description
        )
        assertEquals(Category.Application, projectTemplate.category)
        assertEquals(FormFactor.Mobile, projectTemplate.formFactor)
        val expectedConstraints = listOf(
            TemplateConstraint.AndroidX,
            TemplateConstraint.Material3,
            TemplateConstraint.Kotlin
        )
        assertEquals(expectedConstraints, projectTemplate.constraints)
    }

    @Test
    fun `test default values of parameters`() {
        assertTrue(useDomainLayer.value)
        assertTrue(useRemoteDataSource.value)
        assertTrue(useLocalDataSource.value)
        assertTrue(useRoom.value)
        assertTrue(useRetrofit.value)
    }

    @Test
    fun `test template has correct number of widgets`() {
        assertEquals(11, template.widgets.size)
    }

    @Test
    fun `test file resolution logic used in thumb function`() {
        // This is the same logic used in the thumb function
        val resolvedFile = File("empty-activity").resolve("template_empty_activity.png")

        // Create expected file with system-appropriate separator
        val expectedPath = "empty-activity" + File.separator + "template_empty_activity.png"

        // Verify the path string
        assertEquals(expectedPath, resolvedFile.path)
    }

    @Test
    fun `test recipe calls projectRecipe with correct parameters`() {
        // Setup mocks
        val recipeExecutor = mock(RecipeExecutor::class.java)
        val mockProjectTemplateData = mock(ProjectTemplateData::class.java)
        val packageName = "com.mycompany.myapp"

        // Mocking ModuleTemplateData and setting its behavior
        val moduleData = mock(ModuleTemplateData::class.java).apply {
            `when`(srcDir).thenReturn(File("src"))
            `when`(resDir).thenReturn(File("res"))
            `when`(manifestDir).thenReturn(File("manifest"))
            `when`(rootDir).thenReturn(File("root"))

            // Mock the projectTemplateData to return the mock ProjectTemplateData
            `when`(projectTemplateData).thenReturn(mockProjectTemplateData)
        }

        // Mocking behavior for ProjectTemplateData's rootDir
        `when`(mockProjectTemplateData.rootDir).thenReturn(File("root"))

        // Ambil recipe dari template
        val recipe = projectTemplate.recipe
        assertNotNull(recipe)

        // Execute recipe
        recipe.invoke(recipeExecutor, moduleData)

        // Verifikasi bahwa projectRecipe dipanggil dengan parameter yang benar
        verify(recipeExecutor).projectRecipe(
            moduleData = moduleData,
            packageName = packageName,
            useDomainLayer = true,
            useLocalDataSource = true,
            useRemoteDataSource = true,
            useRoom = true,
            useRetrofit = true
        )
    }
}