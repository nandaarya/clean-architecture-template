package com.github.nandaarya.cleanarchitecturetemplate.source.data.local

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun localDataSourceKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local

import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModelDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val myModelDao: MyModelDao
) {

    suspend fun insertMyModel(item: MyModel) {
        myModelDao.insertMyModel(item)
    }
}
""".trimIndent()