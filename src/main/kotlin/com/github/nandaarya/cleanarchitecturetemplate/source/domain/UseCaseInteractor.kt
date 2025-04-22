package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun useCaseInteractorKt(
    packageName: String,
    useRoom: Boolean,
    useRetrofit: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.usecase

import javax.inject.Inject
import ${escapeKotlinIdentifier(packageName)}.domain.repository.IExampleRepository
${if (useRetrofit) "import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity" else ""}
${if (useRoom) "import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity" else ""}

class ExampleInteractor @Inject constructor(
    private val repository: IExampleRepository
) : ExampleUseCase {
    
    ${if (useRoom)
 """override suspend fun insertMyModel(item: MyModelEntity) {
        repository.insertMyModel(item)
    }""" else ""}
    
    ${if (useRetrofit)
 """override suspend fun register(name: String, email: String, password: String): ExampleResponseEntity {
        return repository.register(name, email, password)
    }""" else ""}
}
""".trimIndent()