package com.github.nandaarya.cleanarchitecturetemplate.source

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
import ${escapeKotlinIdentifier(namespace)}.R

class $activityClass : AppCompatActivity() {
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
    }
}
"""