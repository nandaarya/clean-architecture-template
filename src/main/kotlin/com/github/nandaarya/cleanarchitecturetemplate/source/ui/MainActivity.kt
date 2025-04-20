package com.github.nandaarya.cleanarchitecturetemplate.source.ui

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.getMaterialComponentName
import com.android.tools.idea.wizard.template.renderIf

fun emptyActivityKt(
    packageName: String, namespace: String, activityClass: String, layoutName: String, generateLayout: Boolean, useAndroidX: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import ${getMaterialComponentName("android.support.v7.app.AppCompatActivity", useAndroidX)}
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ${escapeKotlinIdentifier(namespace)}.R
import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class $activityClass : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ${renderIf(generateLayout) {
    """enableEdgeToEdge()
        setContentView(R.layout.$layoutName)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        """
}}
        
        lifecycleScope.launch {
            mainViewModel.myModelsEntity.collect { models ->
                // You could update a UI component with this data
            }
        }

        mainViewModel.insertMyModel(MyModelEntity(1))

        lifecycleScope.launch {
            val response = mainViewModel.registerUser("John", "john@example.com", "password")
            // You could display a success or error message depending on the response
        }
    }
}
""".trimIndent()