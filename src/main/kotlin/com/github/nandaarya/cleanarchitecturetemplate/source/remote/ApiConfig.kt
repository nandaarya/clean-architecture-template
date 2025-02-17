package com.github.nandaarya.cleanarchitecturetemplate.source.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun apiConfigKt(
    packageName: String
) = """
    package ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit

    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory
    import okhttp3.OkHttpClient
    
    class ApiConfig {
        companion object {
            fun getApiService(): ApiService {
                val client = OkHttpClient.Builder().build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("YOUR BASE API URL")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                return retrofit.create(ApiService::class.java)
            }
        }
    }
""".trimIndent()