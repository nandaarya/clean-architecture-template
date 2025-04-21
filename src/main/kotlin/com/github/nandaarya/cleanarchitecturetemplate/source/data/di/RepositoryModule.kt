package com.github.nandaarya.cleanarchitecturetemplate.source.data.di

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun repositoryModuleKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.di

import ${escapeKotlinIdentifier(packageName)}.data.Repository
import ${escapeKotlinIdentifier(packageName)}.domain.repository.IExampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [RetrofitModule::class, RoomModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: Repository): IExampleRepository
}
""".trimIndent()