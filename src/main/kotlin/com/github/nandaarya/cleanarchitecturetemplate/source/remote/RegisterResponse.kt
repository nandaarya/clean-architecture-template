package com.github.nandaarya.cleanarchitecturetemplate.source.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun registerResponseKt(
    packageName: String
) = """
    package ${escapeKotlinIdentifier(packageName)}.data.remote.response

    import com.google.gson.annotations.SerializedName

    // Example Api Response, related to Api Service
    // You can delete it, or just change it
    data class RegisterResponse(

    	@field:SerializedName("error")
    	val error: Boolean? = null,

    	@field:SerializedName("message")
    	val message: String? = null
    )

""".trimIndent()