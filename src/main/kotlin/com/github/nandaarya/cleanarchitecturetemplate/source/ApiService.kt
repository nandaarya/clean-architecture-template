package com.github.nandaarya.cleanarchitecturetemplate.source

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun apiServiceKt(
    packageName: String
) = """
    package ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit

    import ${escapeKotlinIdentifier(packageName)}.data.remote.response.*
    import retrofit2.http.*

    interface ApiService {
    
        // Example Function, You can delete it if you want
        @FormUrlEncoded
        @POST("register")
        suspend fun register(
            @Field("name") name: String,
            @Field("email") email: String,
            @Field("password") password: String
        ): RegisterResponse
    }
""".trimIndent()