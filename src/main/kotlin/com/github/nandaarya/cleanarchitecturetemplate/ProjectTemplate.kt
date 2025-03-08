package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import java.io.File

val projectTemplate
    get() = template {
        name = "Clean Architecture Project"
        minApi = MIN_API
        description = "Creates a new project template with clean architecture"

        category = Category.Application
        formFactor = FormFactor.Mobile
        screens = listOf(WizardUiContext.NewProject, WizardUiContext.NewProjectExtraDetail)
        constraints = listOf(TemplateConstraint.AndroidX, TemplateConstraint.Material3, TemplateConstraint.Kotlin)

        val useDomainLayer: BooleanParameter = booleanParameter {
            name = "Domain Layer (Use Case, Entity, etc)"
            default = true
            help = "If true, Domain Layer file will be generated"
        }

        val useRemoteDataSource: BooleanParameter = booleanParameter {
            name = "RemoteDataSource"
            default = true
            help = "If true, RemoteDataSource file will be generated"
        }

        val useLocalDataSource: BooleanParameter = booleanParameter {
            name = "LocalDataSource"
            default = true
            help = "If true, LocalDataSource file will be generated"
        }

        val useRoom: BooleanParameter = booleanParameter {
            name = "Room"
            default = true
            help = "If true, Room file will be generated"
            enabled = { useLocalDataSource.value }
        }

        val useRetrofit: BooleanParameter = booleanParameter {
            name = "Retrofit"
            default = true
            help = "If true, Retrofit file will be generated"
            enabled = { useRemoteDataSource.value }
        }

        val packageName = defaultPackageNameParameter

        widgets(
            PackageNameWidget(packageName),
            LabelWidget(""),
            LabelWidget("Select the features you want to add!"),
            CheckBoxWidget(useDomainLayer),
            LabelWidget("(Note: Data Layer and UI Layer are always included.)"),
            LabelWidget(""),
            CheckBoxWidget(useLocalDataSource),
            CheckBoxWidget(useRoom),
            LabelWidget(""),
            CheckBoxWidget(useRemoteDataSource),
            CheckBoxWidget(useRetrofit),
        )

        thumb {
            File("empty-activity").resolve("template_empty_activity.png")
        }

        recipe = { data ->
            projectRecipe(
                data as ModuleTemplateData,
                packageName.value,
            )
        }
    }