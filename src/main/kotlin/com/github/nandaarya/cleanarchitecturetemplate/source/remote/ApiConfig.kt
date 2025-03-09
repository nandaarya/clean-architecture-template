package com.github.nandaarya.cleanarchitecturetemplate.source.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun apiConfigKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            return Retrofit.Builder()
                .baseUrl("YOUR BASE API URL")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
""".trimIndent()