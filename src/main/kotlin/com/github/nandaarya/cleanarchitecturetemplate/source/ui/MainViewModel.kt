package com.github.nandaarya.cleanarchitecturetemplate.source.ui

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun mainViewModelKt(
    packageName: String,
    useDomainLayer: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
${if (useDomainLayer) """
    import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity
    import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
    import ${escapeKotlinIdentifier(packageName)}.domain.usecase.ExampleUseCase
""".trimIndent() else """
    import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse
    import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
    import ${escapeKotlinIdentifier(packageName)}.data.Repository
""".trimIndent()}
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    ${if (useDomainLayer) "private val exampleUseCase: ExampleUseCase" else "private val repository: Repository"}
) : ViewModel() {

    ${if (useDomainLayer)
 """private val _myModelsEntity = MutableStateFlow<List<MyModelEntity>>(emptyList())
    val myModelsEntity: StateFlow<List<MyModelEntity>> get() = _myModelsEntity

    suspend fun registerUser(name: String, email: String, password: String): ExampleResponseEntity {
        return exampleUseCase.register(name, email, password)
    }

    fun insertMyModel(item: MyModelEntity) {
        viewModelScope.launch {
            exampleUseCase.insertMyModel(item)
        }
    }"""
else
 """private val _myModelsEntity = MutableStateFlow<List<MyModel>>(emptyList())
    val myModelsEntity: StateFlow<List<MyModel>> get() = _myModelsEntity

    suspend fun registerUser(name: String, email: String, password: String): ExampleResponse {
        return repository.register(name, email, password)
    }

    fun insertMyModel(item: MyModel) {
        viewModelScope.launch {
            repository.insertMyModel(item)
        }
    }"""}
}
""".trimIndent()