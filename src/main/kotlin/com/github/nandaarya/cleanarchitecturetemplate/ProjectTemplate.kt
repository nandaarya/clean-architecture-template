package com.github.nandaarya.cleanarchitecturetemplate

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.Constraint.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import java.io.File

val projectTemplate get() = template {
    name = "Empty Views Activity II"
    minApi = MIN_API
    description = "Creates a new empty activity"

    category = Category.Activity
    formFactor = FormFactor.Mobile
    screens = listOf(WizardUiContext.NewProject)
    constraints = listOf(TemplateConstraint.AndroidX, TemplateConstraint.Material3)

    val generateLayout: BooleanParameter = booleanParameter {
        name = "Generate a Layout File"
        default = true
        help = "If true, a layout file will be generated"
    }
    lateinit var layoutName: StringParameter
    val activityClass: StringParameter = stringParameter {
        name = "Activity Name"
        constraints = listOf(CLASS, UNIQUE, NONEMPTY)
        suggest = {
            layoutToActivity(layoutName.value)
        }
        default = "MainActivity"
        help = "The name of the activity class to create"
    }
    layoutName = stringParameter {
        name = "Layout Name"
        constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
        suggest = {
            activityToLayout(activityClass.value)
        }
        default = "activity_main"
        visible = { generateLayout.value }
        help = "The name of the UI layout to create for the activity"
    }
    val isLauncher: BooleanParameter = booleanParameter {
        name = "Launcher Activity"
        visible = { !isNewModule }
        default = false
        help = "If true, this activity will have a CATEGORY_LAUNCHER intent filter, making it visible in the launcher"
    }
    val packageName = defaultPackageNameParameter

    widgets(
        TextFieldWidget(activityClass),
        CheckBoxWidget(generateLayout),
        TextFieldWidget(layoutName),
        CheckBoxWidget(isLauncher),
        PackageNameWidget(packageName),
        LanguageWidget()
    )

    thumb {
        File("empty-activity").resolve("template_empty_activity.png")
    }

    recipe = { data ->
        projectRecipe(
            data as ModuleTemplateData, activityClass.value, generateLayout.value, layoutName.value, isLauncher.value, packageName.value
        )
    }
}