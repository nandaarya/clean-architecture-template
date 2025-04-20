package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun myModelEntityKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.model

data class MyModelEntity(
    val id: Int
)
""".trimIndent()