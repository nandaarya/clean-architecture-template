package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun entityKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.model

data class ExampleEntity(
    val id: Int,
    val name: String
)
""".trimIndent()