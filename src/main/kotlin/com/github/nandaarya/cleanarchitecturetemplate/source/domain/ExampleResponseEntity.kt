package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun exampleResponseEntityKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.model

data class ExampleResponseEntity(
    val error: Boolean? = null,
    val message: String? = null
)
""".trimIndent()