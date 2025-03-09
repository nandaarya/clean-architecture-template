package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun useCaseKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.usecase

import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleEntity
import kotlinx.coroutines.flow.Flow

interface ExampleUseCase {
    suspend fun getAllExamples(): Flow<List<ExampleEntity>>
    suspend fun getExampleById(id: Int): Flow<ExampleEntity>
    suspend fun addExample(example: ExampleEntity)
    suspend fun deleteExample(example: ExampleEntity)
}
"""