package com.github.nandaarya.cleanarchitecturetemplate.source

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun daoKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MyModelDao {
    @Insert
    suspend fun insertMyModel(item: MyModel)
    
    // Tambahkan metode interaksi dengan database anda disini
}
"""