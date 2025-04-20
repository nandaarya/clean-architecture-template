package com.github.nandaarya.cleanarchitecturetemplate.source.domain.di

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun useCaseModuleKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.domain.di

import ${escapeKotlinIdentifier(packageName)}.domain.usecase.ExampleInteractor
import ${escapeKotlinIdentifier(packageName)}.domain.usecase.ExampleUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun provideExampleUseCase(exampleInteractor: ExampleInteractor): ExampleUseCase
}
""".trimIndent()