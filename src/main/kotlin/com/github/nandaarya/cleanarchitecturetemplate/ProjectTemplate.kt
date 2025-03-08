package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.Constraint.*
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

//        val generateLayout: BooleanParameter = booleanParameter {
//            name = "Generate a Layout File"
//            default = true
//            help = "If true, a layout file will be generated"
//        }
//        lateinit var layoutName: StringParameter
//        val activityClass: StringParameter = stringParameter {
//            name = "Activity Name"
//            constraints = listOf(CLASS, UNIQUE, NONEMPTY)
//            suggest = {
//                layoutToActivity(layoutName.value)
//            }
//            default = "MainActivity"
//            help = "The name of the activity class to create"
//        }
//        layoutName = stringParameter {
//            name = "Layout Name"
//            constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
//            suggest = {
//                activityToLayout(activityClass.value)
//            }
//            default = "activity_main"
//            visible = { generateLayout.value }
//            help = "The name of the UI layout to create for the activity"
//        }
        val packageName = defaultPackageNameParameter

        widgets(
            PackageNameWidget(packageName),
//            TextFieldWidget(activityClass),
//            CheckBoxWidget(generateLayout),
//            TextFieldWidget(layoutName),
        )

        thumb {
            File("empty-activity").resolve("template_empty_activity.png")
        }

        recipe = { data ->
            projectRecipe(
                data as ModuleTemplateData,
//                activityClass.value,
//                generateLayout.value,
//                layoutName.value,
                packageName.value,
            )
        }
    }