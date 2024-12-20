package com.github.nandaarya.cleanarchitecturetemplate.source

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun entityKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "model")
data class MyModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    
    // Tambahkan atau ubah Field baru disini
)
"""