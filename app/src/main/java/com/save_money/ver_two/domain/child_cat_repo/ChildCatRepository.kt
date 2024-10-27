package com.save_money.ver_two.domain.child_cat_repo

import com.save_money.ver_two.database.dao.ChildCategoryDao
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChildCatRepository @Inject constructor(
    private val dao: ChildCategoryDao
) {

    fun getAllChildCategories() = dao.getAllChildCategories()

    suspend fun insertChildCategory(category: ChildCategoryEntity) =
        dao.insertChildCategory(category)

    suspend fun updateChildCategory(category: ChildCategoryEntity) =
        dao.updateChildCategory(category)

}