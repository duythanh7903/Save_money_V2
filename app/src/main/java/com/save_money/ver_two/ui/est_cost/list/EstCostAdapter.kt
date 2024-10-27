package com.save_money.ver_two.ui.est_cost.list

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseRecyclerView
import com.save_money.ver_two.commons.base.ext.convertLongToDateString2
import com.save_money.ver_two.commons.base.ext.formatNumberWithDots
import com.save_money.ver_two.database.entities.RecordEstimateCost
import com.save_money.ver_two.databinding.ItemRecordActualCostBinding

class EstCostAdapter(
    private val contextParams: Context,
): BaseRecyclerView<RecordEstimateCost>() {
    override fun getItemLayout(): Int = R.layout.item_record_actual_cost

    @SuppressLint("SetTextI18n")
    override fun setData(binding: ViewDataBinding, item: RecordEstimateCost, layoutPosition: Int) {
        if (binding is ItemRecordActualCostBinding) {
            Glide.with(contextParams).load(item.categoryEstimateCost.categoryParent.imageRes)
                .into(binding.iconCategory)
            binding.textMoney.text = "- ${contextParams.formatNumberWithDots(item.cost)}"
            binding.textTitle.text = item.noteTitle
            binding.textTime.text = contextParams.convertLongToDateString2(item.time)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<RecordEstimateCost>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}