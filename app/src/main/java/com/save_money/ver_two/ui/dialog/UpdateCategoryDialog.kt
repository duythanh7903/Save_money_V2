package com.save_money.ver_two.ui.dialog

import android.content.Context
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseDialog
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.databinding.DialogUpdateCategoryBinding

class UpdateCategoryDialog(
    private val context: Context,
    var onUpdateCategory: ((title: String) -> Unit)? = null,
    var onInputNull: (() -> Unit)? = null
) : BaseDialog<DialogUpdateCategoryBinding>(context) {

    override fun getLayoutDialog(): Int = R.layout.dialog_update_category

    override fun initViews() {
        super.initViews()
        setCancelable(false)
    }

    override fun onClickViews() {
        binding.buttonSave.click {
            val title = binding.inputCategoryName.text.toString().trim()
            if (title.isEmpty()) {
                onInputNull?.invoke()
            } else {
                onUpdateCategory?.invoke(title)
                dismiss()
            }
        }
    }
}