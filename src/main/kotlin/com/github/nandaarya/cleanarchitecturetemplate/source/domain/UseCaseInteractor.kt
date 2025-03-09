package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun useCaseInteractorKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.usecase

import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleEntity
import ${escapeKotlinIdentifier(packageName)}.domain.repository.IExampleRepository
import kotlinx.coroutines.flow.Flow

class ExampleInteractor(private val repository: IExampleRepository) : ExampleUseCase {
    override suspend fun getAllExamples(): Flow<List<ExampleEntity>> = repository.getAllExamples()
    override suspend fun getExampleById(id: Int): Flow<ExampleEntity> = repository.getExampleById(id)
    override suspend fun addExample(example: ExampleEntity) = repository.addExample(example)
    override suspend fun deleteExample(example: ExampleEntity) = repository.deleteExample(example)
}
"""