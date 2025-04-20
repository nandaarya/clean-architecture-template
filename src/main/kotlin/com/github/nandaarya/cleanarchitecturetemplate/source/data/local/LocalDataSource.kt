package com.github.nandaarya.cleanarchitecturetemplate.source.data.local

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun localDataSourceKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local

import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModelDao
import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val myModelDao: MyModelDao
) {

    suspend fun insertMyModel(item: MyModelEntity) {
        val myModel = MyModel(
            id = item.id
        )
        myModelDao.insertMyModel(myModel)
    }
}
""".trimIndent()