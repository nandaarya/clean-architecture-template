package com.github.nandaarya.cleanarchitecturetemplate.source.data.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun remoteDataSourceKt(
    packageName: String,
    useDomainLayer: Boolean,
    useRetrofit: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.data.remote

import javax.inject.Inject
${if (useDomainLayer && useRetrofit) """
    import ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit.ApiService
    import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
""".trimIndent() 
else if (!useDomainLayer && useRetrofit) """
    import ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit.ApiService
    import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse
""".trimIndent() 
else ""}

class RemoteDataSource @Inject constructor(
    ${if (useRetrofit) "private val apiService: ApiService" else ""}
) {
    
    ${if (useRetrofit) 
 """suspend fun register(name: String, email: String, password: String): ${if (useDomainLayer) "ExampleResponseEntity" else "ExampleResponse"} {
        ${if (useDomainLayer)
     """val response = apiService.register(name, email, password)
        return ExampleResponseEntity(
            error = response.error,
            message = response.message
        )"""
        else
     """return apiService.register(name, email, password)"""}
    }   
    """ else ""}
}
""".trimIndent()