package com.github.nandaarya.cleanarchitecturetemplate.source.remote

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun exampleResponseKt(
    packageName: String
) = """
    package ${escapeKotlinIdentifier(packageName)}.data.remote.response

    import com.google.gson.annotations.SerializedName

    data class ExampleResponse(

    	@field:SerializedName("error")
    	val error: Boolean? = null,

    	@field:SerializedName("message")
    	val message: String? = null
    )
""".trimIndent()