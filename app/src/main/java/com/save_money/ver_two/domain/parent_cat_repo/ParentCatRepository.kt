package com.save_money.ver_two.domain.parent_cat_repo

import com.save_money.ver_two.database.dao.ParentCategoryDao
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParentCatRepository @Inject constructor(
    private val dao: ParentCategoryDao
) {

    val listParentCate = dao.getAllCategoryParents()

    suspend fun insertAllCategoryParent(categories: MutableList<ParentCategoryEntity>) {
        dao.insertAllCategoryParent(categories)
    }

}