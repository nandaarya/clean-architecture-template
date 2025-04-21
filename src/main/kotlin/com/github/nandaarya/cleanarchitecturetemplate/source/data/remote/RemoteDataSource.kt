package com.github.nandaarya.cleanarchitecturetemplate.source.data.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun remoteDataSourceKt(
    packageName: String,
    useDomainLayer: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.data.remote

import ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit.ApiService
${if (useDomainLayer) """
    import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
""".trimIndent() else """
    import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse
""".trimIndent()}
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun register(name: String, email: String, password: String): ${if (useDomainLayer) "ExampleResponseEntity" else "ExampleResponse"} {
        ${if (useDomainLayer)
     """val response = apiService.register(name, email, password)
        return ExampleResponseEntity(
            error = response.error,
            message = response.message
        )""" 
        else
     """return apiService.register(name, email, password)"""}
    }
}
""".trimIndent()