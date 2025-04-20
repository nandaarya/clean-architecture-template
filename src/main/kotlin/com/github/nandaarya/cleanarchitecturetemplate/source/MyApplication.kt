package com.github.nandaarya.cleanarchitecturetemplate.source

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun myApplicationKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()
""".trimIndent()