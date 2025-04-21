package com.github.nandaarya.cleanarchitecturetemplate.source.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun repositoryKt(
    packageName: String,
    useDomainLayer: Boolean
) = """ 
package ${escapeKotlinIdentifier(packageName)}.data

import ${escapeKotlinIdentifier(packageName)}.data.local.LocalDataSource
import ${escapeKotlinIdentifier(packageName)}.data.remote.RemoteDataSource
${if (useDomainLayer) """
    import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
    import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
    import ${escapeKotlinIdentifier(packageName)}.domain.repository.IExampleRepository
""".trimIndent() else """
    import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse
    import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
""".trimIndent()}
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
)${if (useDomainLayer) ": IExampleRepository " else " "}{

    ${if (useDomainLayer) 
 """override suspend fun insertMyModel(item: MyModelEntity) {
        localDataSource.insertMyModel(item)
    }

    override suspend fun register(name: String, email: String, password: String): ExampleResponseEntity {
        return remoteDataSource.register(name, email, password)
    }"""
else 
 """suspend fun insertMyModel(item: MyModel) {
        localDataSource.insertMyModel(item)
    }

    suspend fun register(name: String, email: String, password: String): ExampleResponse {
        return remoteDataSource.register(name, email, password)
    }"""}
}
""".trimIndent()