package com.github.nandaarya.cleanarchitecturetemplate.source.data.local

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun localDataSourceKt(
    packageName: String,
    useDomainLayer: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local

import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModelDao
${if (useDomainLayer) """
    import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
    import javax.inject.Inject
""".trimIndent() else """
    import javax.inject.Inject
""".trimIndent()}

class LocalDataSource @Inject constructor(
    private val myModelDao: MyModelDao
) {

    suspend fun insertMyModel(item: ${if (useDomainLayer) "MyModelEntity" else "MyModel"}) {
        ${if (useDomainLayer)
     """val myModel = MyModel(
            id = item.id
        )
        myModelDao.insertMyModel(myModel)"""
        else
     """myModelDao.insertMyModel(item)"""}
    }
}
""".trimIndent()