package com.github.nandaarya.cleanarchitecturetemplate.source.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun repositoryKt(
    packageName: String
) = """
    package ${escapeKotlinIdentifier(packageName)}.data
    
    import ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit.ApiService
    
    class Repository(
        private val apiService: ApiService,
    
        ) {
        suspend fun register(name: String, email: String, password: String): ExampleResponse {
            return apiService.register(name, email, password)
        }
    }
""".trimIndent()