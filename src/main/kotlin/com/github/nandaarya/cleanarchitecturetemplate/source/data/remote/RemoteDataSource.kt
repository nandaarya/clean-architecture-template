package com.github.nandaarya.cleanarchitecturetemplate.source.data.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun remoteDataSourceKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.remote

import ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit.ApiService
import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun register(name: String, email: String, password: String): ExampleResponseEntity {
        val response = apiService.register(name, email, password)
        return ExampleResponseEntity(
            error = response.error,
            message = response.message
        )
    }
}
""".trimIndent()