package com.save_money.ver_two.ui.onb

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseRecyclerView
import com.save_money.ver_two.databinding.ItemOnBoardingBinding

class OnbAdapter : BaseRecyclerView<OnbModel>() {
    override fun getItemLayout(): Int = R.layout.item_on_boarding

    override fun setData(binding: ViewDataBinding, item: OnbModel, layoutPosition: Int) {
        if (binding is ItemOnBoardingBinding) {
            binding.textTitleOnboarding.text = context?.getString(item.titleRes) ?: ""
            binding.textDescriptionOnboarding.text = context?.getString(item.descriptionRes) ?: ""
            binding.imgGuide.setImageResource(item.imageRes)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<OnbModel>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}