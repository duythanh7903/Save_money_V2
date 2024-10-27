package com.save_money.ver_two.ui.language

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseRecyclerView
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.databinding.ItemLanguageBinding

class LanguageAdapter(
    private val contextParams: Context,
    private val onLanguageChosen: (languageModel: LanguageModel, index: Int) -> Unit
) : BaseRecyclerView<LanguageModel>() {

    var indexSelected: Int = -1
        set(value) {
            field = value
            notifyItemChanged(value)
        }

    override fun getItemLayout(): Int = R.layout.item_language

    override fun setData(binding: ViewDataBinding, item: LanguageModel, layoutPosition: Int) {
        if (binding is ItemLanguageBinding) {
            Glide.with(contextParams).load(item.flagRes).into(binding.iconFlag)
            binding.textCountryName.text = item.countriesName
            binding.iconTick.isActivated = (indexSelected == layoutPosition)
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: LanguageModel, layoutPosition: Int) {
        if (binding is ItemLanguageBinding) {
            binding.root.click { onLanguageChosen.invoke(obj, layoutPosition) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<LanguageModel>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}