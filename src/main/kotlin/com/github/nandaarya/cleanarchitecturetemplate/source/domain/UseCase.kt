package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun useCaseKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.usecase

import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity

interface ExampleUseCase{
    suspend fun insertMyModel(item: MyModelEntity)
    suspend fun register(name: String, email: String, password: String): ExampleResponseEntity
}
""".trimIndent()