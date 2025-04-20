package com.github.nandaarya.cleanarchitecturetemplate.source.data.di

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun roomModuleKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.di

import android.content.Context
import androidx.room.Room
import ${escapeKotlinIdentifier(packageName)}.data.local.room.DummyDatabase
import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DummyDatabase {
        return Room.databaseBuilder(
            context,
            DummyDatabase::class.java,
            "local_database.db"
        ).build()
    }

    @Provides
    fun provideMyModelDao(database: DummyDatabase): MyModelDao {
        return database.myModelDao()
    }
}
""".trimIndent()