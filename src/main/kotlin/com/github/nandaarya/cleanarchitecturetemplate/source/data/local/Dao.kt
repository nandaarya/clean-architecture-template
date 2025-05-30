package com.github.nandaarya.cleanarchitecturetemplate.source.data.local

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun daoKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local.room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MyModelDao {
    @Insert
    suspend fun insertMyModel(item: MyModel)
}
""".trimIndent()