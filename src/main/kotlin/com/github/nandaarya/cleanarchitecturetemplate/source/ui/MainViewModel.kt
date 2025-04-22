package com.github.nandaarya.cleanarchitecturetemplate.source.ui

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun mainViewModelKt(
    packageName: String,
    useDomainLayer: Boolean,
    useRoom: Boolean,
    useRetrofit: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.ui

import androidx.lifecycle.ViewModel
${if (useDomainLayer) """
    ${if (useRetrofit) "import ${escapeKotlinIdentifier(packageName)}.domain.model.ExampleResponseEntity" else ""}
    ${if (useRoom) "import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity" else ""}
    import ${escapeKotlinIdentifier(packageName)}.domain.usecase.ExampleUseCase
""".trimIndent() else """
    ${if (useRetrofit) "import ${escapeKotlinIdentifier(packageName)}.data.remote.response.ExampleResponse" else ""}
    ${if (useRoom) "import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel" else ""}
    import ${escapeKotlinIdentifier(packageName)}.data.Repository
""".trimIndent()}
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
${if (useRoom) """
    import androidx.lifecycle.viewModelScope
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
""".trimIndent() else ""}

@HiltViewModel
class MainViewModel @Inject constructor(
    ${if (useDomainLayer) "private val exampleUseCase: ExampleUseCase" else "private val repository: Repository"}
) : ViewModel() {

    ${if (useDomainLayer)
 """${if (useRoom)
 """private val _myModelsEntity = MutableStateFlow<List<MyModelEntity>>(emptyList())
    val myModelsEntity: StateFlow<List<MyModelEntity>> get() = _myModelsEntity
    
    fun insertMyModel(item: MyModelEntity) {
        viewModelScope.launch {
            exampleUseCase.insertMyModel(item)
        }
    }""" 
 else ""}
    ${if (useRetrofit)
 """suspend fun register(name: String, email: String, password: String): ExampleResponseEntity {
        return exampleUseCase.register(name, email, password)
    }""" 
 else ""}
 """
else
 """${if (useRoom) 
 """private val _myModels = MutableStateFlow<List<MyModel>>(emptyList())
    val myModels: StateFlow<List<MyModel>> get() = _myModels
    
    fun insertMyModel(item: MyModel) {
        viewModelScope.launch {
            repository.insertMyModel(item)
        }
    }""" 
 else ""}
    ${if (useRetrofit) 
 """suspend fun register(name: String, email: String, password: String): ExampleResponse {
        return repository.register(name, email, password)
    }""" 
 else ""}
 """}
}
""".trimIndent()