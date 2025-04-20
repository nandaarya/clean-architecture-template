package com.github.nandaarya.cleanarchitecturetemplate.source.data.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun remoteDataSourceKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.remote

import ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit.ApiService
import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun register(name: String, email: String, password: String): ExampleResponse {
        return apiService.register(name, email, password)
    }
}
""".trimIndent()