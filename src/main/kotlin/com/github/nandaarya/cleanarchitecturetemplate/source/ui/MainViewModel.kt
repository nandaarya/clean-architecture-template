package com.github.nandaarya.cleanarchitecturetemplate.source.ui

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun mainViewModelKt(
    packageName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
import ${escapeKotlinIdentifier(packageName)}.domain.usecase.ExampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exampleUseCase: ExampleUseCase
) : ViewModel() {

    private val _myModelsEntity = MutableStateFlow<List<MyModelEntity>>(emptyList())
    val myModelsEntity: StateFlow<List<MyModelEntity>> get() = _myModelsEntity

    suspend fun registerUser(name: String, email: String, password: String): ExampleResponseEntity {
        return exampleUseCase.registerUser(name, email, password)
    }

    fun insertMyModel(item: MyModelEntity) {
        viewModelScope.launch {
            exampleUseCase.insertMyModel(item)
        }
    }
}
""".trimIndent()