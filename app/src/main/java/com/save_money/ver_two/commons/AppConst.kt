package com.save_money.ver_two.commons

import com.save_money.ver_two.R
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity.Companion.TYPE_EXPENSE
import com.save_money.ver_two.database.entities.ParentCategoryEntity.Companion.TYPE_INCOME

object AppConst {

    internal const val CHANEL_ID_NOTI = "com.duylt.money.management"

    internal const val NOTI_ID = 1


    fun getAllParentCategory() = mutableListOf(
        ParentCategoryEntity(0, "Birthday", R.drawable.icon_cate_bag, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Children day", R.drawable.icon_cate_nurses, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Grandparents", R.drawable.icon_cate_house, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Investment", R.drawable.icon_cate_water, TYPE_EXPENSE),
        ParentCategoryEntity(0, "New year", R.drawable.icon_cate_beauty, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Other", R.drawable.icon_cate_water, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Parents", R.drawable.icon_cate_hawaiian_shirt, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Salary", R.drawable.icon_cate_diet, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Saving", R.drawable.icon_cate_water, TYPE_EXPENSE),
        ParentCategoryEntity(0, "Study bonus", R.drawable.icon_cate_studying, TYPE_EXPENSE),

        ParentCategoryEntity(0, "Activity", R.drawable.father, TYPE_INCOME),
        ParentCategoryEntity(0, "Assessories", R.drawable.dollar, TYPE_INCOME),
        ParentCategoryEntity(0, "Birthday", R.drawable.handsome, TYPE_INCOME),
        ParentCategoryEntity(0, "Clothes", R.drawable.team, TYPE_INCOME),
        ParentCategoryEntity(0, "Foods", R.drawable.lotus, TYPE_INCOME),
        ParentCategoryEntity(0, "Other", R.drawable.salary, TYPE_INCOME),
        ParentCategoryEntity(0, "Phone expenses", R.drawable.coding, TYPE_INCOME),
        ParentCategoryEntity(0, "Study fees", R.drawable.mother, TYPE_INCOME),
        ParentCategoryEntity(0, "Taxi", R.drawable.salary, TYPE_INCOME)
    )

}