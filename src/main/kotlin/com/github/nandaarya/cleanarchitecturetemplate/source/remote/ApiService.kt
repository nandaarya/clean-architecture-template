package com.github.nandaarya.cleanarchitecturetemplate.source.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun apiServiceKt(
    packageName: String
) = """
    package ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit

    import ${escapeKotlinIdentifier(packageName)}.data.remote.response.*
    import retrofit2.http.*

    interface ApiService {
    
        @FormUrlEncoded
        @POST("register")
        suspend fun register(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String
        ): ExampleResponse
    }
""".trimIndent()