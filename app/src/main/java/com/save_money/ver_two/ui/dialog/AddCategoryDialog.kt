package com.save_money.ver_two.ui.dialog

import android.content.Context
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseDialog
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.databinding.DialogAddCategoryBinding
import com.save_money.ver_two.ui.adapter.ParentCategoryAdapter

class AddCategoryDialog(
    private val context: Context,
    private val adapterCategoryParent: ParentCategoryAdapter,
    private val onInputNull: () -> Unit,
    private val onSaveCategory: (categoryName: String) -> Unit,
    private val onCancelSave: () -> Unit
) : BaseDialog<DialogAddCategoryBinding>(context) {

    override fun getLayoutDialog(): Int = R.layout.dialog_add_category

    override fun initViews() {
        initRcvCategoryParent()
        setCancelable(false)
    }

    override fun onClickViews() {
        binding.apply {
            iconClear.click {
                onCancelSave.invoke()
                dismiss()
            }

            buttonSave.click {
                val categoryName = inputCategoryName.text.toString().trim()
                if (categoryName.isEmpty()) onInputNull.invoke()
                else {
                    onSaveCategory.invoke(categoryName)
                    binding.inputCategoryName.text.clear()
                    dismiss()
                }
            }
        }
    }

    private fun initRcvCategoryParent() =
        binding.rcvCategoryParent.apply {
            adapter = adapterCategoryParent
        }
}