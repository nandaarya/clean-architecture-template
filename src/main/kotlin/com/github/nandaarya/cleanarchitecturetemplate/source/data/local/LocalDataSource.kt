package com.github.nandaarya.cleanarchitecturetemplate.source.data.local

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun localDataSourceKt(
    packageName: String,
    useDomainLayer: Boolean,
    useRoom: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}.data.local

import javax.inject.Inject
${if (useDomainLayer && useRoom) """
    import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
    import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModelDao
    import ${escapeKotlinIdentifier(packageName)}.domain.model.MyModelEntity
""".trimIndent() 
else if (!useDomainLayer && useRoom) """
    import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModel
    import ${escapeKotlinIdentifier(packageName)}.data.local.room.MyModelDao
""".trimIndent() else ""}

class LocalDataSource @Inject constructor(
    ${if (useRoom) "private val myModelDao: MyModelDao" else ""}
) {

    ${if (useRoom) 
 """suspend fun insertMyModel(item: ${if (useDomainLayer) "MyModelEntity" else "MyModel"}) {
        ${if (useDomainLayer)
     """val myModel = MyModel(
            id = item.id
        )
        myModelDao.insertMyModel(myModel)"""
        else
     """myModelDao.insertMyModel(item)"""}
    } 
    """ else ""} 
}
""".trimIndent()