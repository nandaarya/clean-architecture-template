package com.github.nandaarya.cleanarchitecturetemplate.source.data.local

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun databaseKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MyModel::class],
    version = 1,
    exportSchema = false
)
abstract class DummyDatabase : RoomDatabase() {
    abstract fun myModelDao(): MyModelDao
}
""".trimIndent()