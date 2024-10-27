package com.save_money.ver_two.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseRecyclerView
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import com.save_money.ver_two.databinding.ItemCategoryChildBinding

class ChildCategoryAdapter(
    private val contextParams: Context,
    private val onClickItem: (category: ChildCategoryEntity, index: Int) -> Unit,
    private val onEditItem: (category: ChildCategoryEntity, index: Int) -> Unit
) : BaseRecyclerView<ChildCategoryEntity>() {

    var indexSelected: Int = -1
        set(value) {
            field = value
            notifyItemChanged(indexSelected)
        }

    override fun getItemLayout(): Int = R.layout.item_category_child

    override fun setData(binding: ViewDataBinding, item: ChildCategoryEntity, layoutPosition: Int) {
        if (binding is ItemCategoryChildBinding) {
            Glide.with(contextParams).load(
                item.iconCategoryChild.ifEmpty { item.categoryParent.imageRes }
            ).into(binding.iconCategoryChild)
            binding.textCategoryName.text =
                item.categoryName.ifEmpty { item.categoryParent.categoryName }
            binding.textCategoryName.isSelected = true

            binding.textCategoryName.setTextColor(contextParams.getColor(
                if (layoutPosition == indexSelected) R.color.text_orange
                else R.color.text_dark_blue)
            )
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: ChildCategoryEntity, layoutPosition: Int) {
        if (binding is ItemCategoryChildBinding) {
            binding.containerImage.click { onClickItem.invoke(obj, layoutPosition) }
            binding.iconEditCate.click { onEditItem.invoke(obj, layoutPosition) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ChildCategoryEntity>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}