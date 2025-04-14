package com.github.nandaarya.cleanarchitecturetemplate.source.data.local

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun modelKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "model")
data class MyModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
)
"""