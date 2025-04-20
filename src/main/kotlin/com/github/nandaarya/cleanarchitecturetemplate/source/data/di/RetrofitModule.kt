package com.github.nandaarya.cleanarchitecturetemplate.source.data.di

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun retrofitModuleKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.data.di

import ${escapeKotlinIdentifier(packageName)}.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("YOUR_BASE_API_URL")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
""".trimIndent()