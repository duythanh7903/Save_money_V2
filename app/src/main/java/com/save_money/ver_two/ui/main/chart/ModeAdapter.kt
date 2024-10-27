package com.save_money.ver_two.ui.main.chart

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseRecyclerView
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.databinding.ItemModeBinding

class ModeAdapter(
    private val onItemChosen: (String, Int) -> Unit
) : BaseRecyclerView<String>() {
    override fun getItemLayout(): Int = R.layout.item_mode

    override fun setData(binding: ViewDataBinding, item: String, layoutPosition: Int) {
        if (binding is ItemModeBinding) {
            binding.textMode.text = item
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: String, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemModeBinding) {
            binding.root.click {
                onItemChosen.invoke(obj, layoutPosition)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<String>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}