package com.github.nandaarya.cleanarchitecturetemplate.source

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun databaseKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MyModel::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun myModelDao(): MyModelDao

    companion object {
        @Volatile
        private var instance: Database? = null

        fun getInstance(context: Context): Database {
            return synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    Database::class.java,
                    "local_database.db"
                )
                    .build()
            }
        }
    }
}
"""