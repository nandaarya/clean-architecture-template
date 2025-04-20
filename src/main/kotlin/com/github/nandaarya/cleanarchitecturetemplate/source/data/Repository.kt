package com.github.nandaarya.cleanarchitecturetemplate.source.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun repositoryKt(
    packageName: String
) = """ 
package ${escapeKotlinIdentifier(packageName)}.data

import ${escapeKotlinIdentifier(packageName)}.data.local.LocalDataSource
import ${escapeKotlinIdentifier(packageName)}.data.remote.RemoteDataSource
import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
import ${escapeKotlinIdentifier(packageName)}.domain.repository.IExampleRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): IExampleRepository {

    override suspend fun insertMyModel(item: MyModelEntity) {
        localDataSource.insertMyModel(item)
    }

    override suspend fun register(name: String, email: String, password: String): ExampleResponseEntity {
        return remoteDataSource.register(name, email, password)
    }
}
""".trimIndent()