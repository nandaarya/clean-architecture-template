package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun useCaseInteractorKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.usecase

import ${escapeKotlinIdentifier(packageName)}.domain.repository.IExampleRepository
import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
import javax.inject.Inject

class ExampleInteractor @Inject constructor(
    private val repository: IExampleRepository
) : ExampleUseCase {

    override suspend fun insertMyModel(item: MyModelEntity) {
        repository.insertMyModel(item)
    }

    override suspend fun registerUser(name: String, email: String, password: String): ExampleResponseEntity {
        return repository.register(name, email, password)
    }
}
""".trimIndent()