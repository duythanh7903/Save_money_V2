package com.save_money.ver_two.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseRecyclerView
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import com.save_money.ver_two.databinding.ItemCategoryParentBinding

class ParentCategoryAdapter(
    private val contextParams: Context,
    private val onClickItem: (category: ParentCategoryEntity, index: Int) -> Unit
) : BaseRecyclerView<ParentCategoryEntity>() {

    var indexSelected: Int = -1
        set(value) {
            field = value
            notifyItemChanged(indexSelected)
        }

    override fun getItemLayout(): Int = R.layout.item_category_parent

    override fun setData(binding: ViewDataBinding, item: ParentCategoryEntity, layoutPosition: Int) {
        if (binding is ItemCategoryParentBinding) {
            Glide.with(contextParams).load(item.imageRes).into(binding.iconCategoryParent)
            binding.textCategoryName.text = item.categoryName
            binding.layoutContainer.isActivated = layoutPosition == indexSelected
            binding.textCategoryName.setTextColor(
                contextParams.getColor(
                    if (layoutPosition == indexSelected) R.color.white
                    else R.color.text_dark_blue
                )
            )
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: ParentCategoryEntity, layoutPosition: Int) {
        if (binding is ItemCategoryParentBinding) {
            binding.root.click { onClickItem.invoke(obj, layoutPosition) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ParentCategoryEntity>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}