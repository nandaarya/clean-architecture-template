package com.github.nandaarya.cleanarchitecturetemplate.source.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun repositoryKt(
    packageName: String
) = """ 
package ${escapeKotlinIdentifier(packageName)}.data

import ${escapeKotlinIdentifier(packageName)}.data.local.LocalDataSource
import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
import ${escapeKotlinIdentifier(packageName)}.data.remote.RemoteDataSource
import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun insertMyModel(item: MyModel) {
        localDataSource.insertMyModel(item)
    }

    suspend fun register(name: String, email: String, password: String): ExampleResponse {
        return remoteDataSource.register(name, email, password)
    }
}
""".trimIndent()