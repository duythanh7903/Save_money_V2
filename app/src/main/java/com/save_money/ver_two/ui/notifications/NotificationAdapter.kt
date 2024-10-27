package com.save_money.ver_two.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseRecyclerView
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.convertLongToHHmm
import com.save_money.ver_two.commons.base.ext.hide
import com.save_money.ver_two.commons.base.ext.show
import com.save_money.ver_two.database.entities.NotificationEntity
import com.save_money.ver_two.databinding.ItemNotiBinding

class NotificationAdapter(
    private val contextParams: Context,
    private val onClickItem: (item: NotificationEntity, index: Int) -> Unit
) : BaseRecyclerView<NotificationEntity>() {
    override fun getItemLayout(): Int = R.layout.item_noti

    override fun setData(binding: ViewDataBinding, item: NotificationEntity, layoutPosition: Int) {
        if (binding is ItemNotiBinding) {
            binding.textTitle.text = item.title
            binding.textTitle.isSelected = true
            binding.textContent.text = item.content
            binding.textTime.text = contextParams.convertLongToHHmm(item.time)
            if (item.isRead) binding.iconWarningNotRead.hide() else binding.iconWarningNotRead.show()
        }
    }

    override fun onClickViews(
        binding: ViewDataBinding,
        obj: NotificationEntity,
        layoutPosition: Int
    ) {
        if (binding is ItemNotiBinding) {
            binding.root.click { onClickItem.invoke(obj, layoutPosition) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<NotificationEntity>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}