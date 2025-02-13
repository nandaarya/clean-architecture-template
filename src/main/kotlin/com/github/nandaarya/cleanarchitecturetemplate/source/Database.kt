package com.github.nandaarya.cleanarchitecturetemplate.source

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun databaseKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MyModel::class],
    version = 1
)
abstract class DummyDatabase : RoomDatabase() {

    abstract fun myModelDao(): MyModelDao

    companion object {
        @Volatile
        private var instance: DummyDatabase? = null

        fun getInstance(context: Context): DummyDatabase {
            return synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    DummyDatabase::class.java,
                    "local_database.db"
                )
                    .build()
            }
        }
    }
}
"""