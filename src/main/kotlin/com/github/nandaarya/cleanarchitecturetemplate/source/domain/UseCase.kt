package com.github.nandaarya.cleanarchitecturetemplate.source.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun useCaseKt(
    packageName: String,
    useRoom: Boolean,
    useRetrofit: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.usecase

${if (useRetrofit) "import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity" else ""}
${if (useRoom) "import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity" else ""}

interface ExampleUseCase{
    ${if (useRoom)
   "suspend fun insertMyModel(item: MyModelEntity)" else ""}
    ${if (useRetrofit)
   "suspend fun register(name: String, email: String, password: String): ExampleResponseEntity" else ""}
}
""".trimIndent()