package com.github.nandaarya.cleanarchitecturetemplate.source.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun repositoryKt(
    packageName: String,
    useDomainLayer: Boolean,
    useLocalDataSource: Boolean,
    useRemoteDataSource: Boolean,
    useRoom: Boolean,
    useRetrofit: Boolean
) = """ 
package ${escapeKotlinIdentifier(packageName)}.data

${when {
    useLocalDataSource && useRemoteDataSource -> """
        import ${escapeKotlinIdentifier(packageName)}.data.local.LocalDataSource
        import ${escapeKotlinIdentifier(packageName)}.data.remote.RemoteDataSource
    """.trimIndent()
    useLocalDataSource -> "import ${escapeKotlinIdentifier(packageName)}.data.local.LocalDataSource"
    useRemoteDataSource -> "import ${escapeKotlinIdentifier(packageName)}.data.remote.RemoteDataSource"
    else -> ""
}}
${if (useDomainLayer) """
    ${if (useRetrofit) "import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity" else ""}
    ${if (useRoom) "import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity" else ""}
    import ${escapeKotlinIdentifier(packageName)}.domain.repository.IExampleRepository
""".trimIndent() else """
    ${if (useRetrofit) "import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse" else ""}
    ${if (useRoom) "import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel" else ""}
""".trimIndent()}
import javax.inject.Inject

class Repository @Inject constructor(
    ${when {
    useLocalDataSource && useRemoteDataSource -> """
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource
    """.trimIndent()
    useLocalDataSource -> "private val localDataSource: LocalDataSource"
    useRemoteDataSource -> "private val remoteDataSource: RemoteDataSource"
    else -> ""
}}
)${if (useDomainLayer) ": IExampleRepository " else " "}{

    ${if (useRoom) 
 """${if (useDomainLayer) "override" else ""} 
    suspend fun insertMyModel(item: ${if (useDomainLayer) "MyModelEntity" else "MyModel"}) {
        localDataSource.insertMyModel(item)
    }
    """ else ""}
    
    ${if (useRetrofit) 
 """${if (useDomainLayer) "override" else ""} 
    suspend fun register(name: String, email: String, password: String): ${if (useDomainLayer) "ExampleResponseEntity" else "ExampleResponse"} {
        return remoteDataSource.register(name, email, password)
    }
    """ else ""}
}
""".trimIndent()