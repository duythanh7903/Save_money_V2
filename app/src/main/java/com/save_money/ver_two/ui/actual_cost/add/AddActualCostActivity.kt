package com.save_money.ver_two.ui.actual_cost.add

import android.content.Intent
import androidx.activity.viewModels
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.convertLongToDateString
import com.save_money.ver_two.commons.base.ext.showToast
import com.save_money.ver_two.database.entities.ChildCategoryEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity
import com.save_money.ver_two.database.entities.ParentCategoryEntity.Companion.TYPE_EXPENSE
import com.save_money.ver_two.database.entities.RecordActualCost
import com.save_money.ver_two.databinding.ActivityAddActualCostBinding
import com.save_money.ver_two.ui.actual_cost.ActualCostViewModel
import com.save_money.ver_two.ui.actual_cost.list.ActualCostsActivity
import com.save_money.ver_two.ui.adapter.ChildCategoryAdapter
import com.save_money.ver_two.ui.adapter.ParentCategoryAdapter
import com.save_money.ver_two.ui.dialog.AddCategoryDialog
import com.save_money.ver_two.ui.dialog.UpdateCategoryDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActualCostActivity :
    BaseActivity<ActivityAddActualCostBinding>(R.layout.activity_add_actual_cost) {

    private val dialogUpdateCategory: UpdateCategoryDialog by lazy {
        UpdateCategoryDialog(this)
    }
    private val adapterParentCat: ParentCategoryAdapter by lazy {
        ParentCategoryAdapter(
            contextParams = this,
            onClickItem = { category, index ->
                val indexSelectedBefore = this.adapterParentCat.indexSelected
                this.adapterParentCat.indexSelected = index
                this.adapterParentCat.notifyItemChanged(indexSelectedBefore)

                this.parentCatSelected = category
            },
        )
    }
    private val dialogAddCategory: AddCategoryDialog by lazy {
        AddCategoryDialog(
            context = this,
            adapterCategoryParent = adapterParentCat,
            onInputNull = { showToast(R.string.this_field_cannot_be_left_blank) },
            onSaveCategory = { categoryName ->
                if (parentCatSelected == null) {
                    showToast(R.string.category_not_selected)
                } else {
                    // add to room
                    viewModel.insertChildCate(categoryName, parentCatSelected!!)

                    // refresh all
                    parentCatSelected = adapterParentCat.list[0]
                    val indexSelectedBefore = adapterParentCat.indexSelected
                    adapterParentCat.indexSelected = 0
                    adapterParentCat.notifyItemChanged(indexSelectedBefore)
                }
            }, onCancelSave = {
                parentCatSelected = adapterParentCat.list[0]
                val indexSelectedBefore = adapterParentCat.indexSelected
                adapterParentCat.indexSelected = 0
                adapterParentCat.notifyItemChanged(indexSelectedBefore)
            }
        )
    }
    private val childCatAdapter: ChildCategoryAdapter by lazy {
        ChildCategoryAdapter(
            this,
            onClickItem = { cat, index ->
                if (cat.id == 0L) {
                    showToast(R.string.this_is_sample_item)
                } else {
                    val indexSelectedBefore = this.childCatAdapter.indexSelected
                    this.childCatAdapter.indexSelected = index
                    this.childCatAdapter.notifyItemChanged(indexSelectedBefore)
                    childCatSelected = cat
                }
            },
            onEditItem = { cat, _ ->
                if (cat.id == 0L) {
                    showToast(R.string.this_is_sample_item)
                } else {
                    dialogUpdateCategory.apply {
                        onInputNull = { showToast(R.string.this_field_cannot_be_left_blank) }
                        onUpdateCategory = { title ->
                            cat.categoryName = title
                            viewModel.updateChildCate(cat)
                        }
                        show()
                    }
                }
            }
        )
    }

    private var parentCatSelected: ParentCategoryEntity? = null
    private var childCatSelected: ChildCategoryEntity? = null

    private val viewModel: ActualCostViewModel by viewModels()

    override fun initView() {
        clickViews()
        observer()
        initChildCat()
        initInputTime()
    }

    private fun initChildCat() =
        binding.rcvCategory.apply {
            adapter = childCatAdapter
        }

    private fun clickViews() = binding.apply {
        iconList.click {
            startActivity(Intent(this@AddActualCostActivity, ActualCostsActivity::class.java))
        }

        iconBack.click { finish() }

        textAddMore.click { dialogAddCategory.show() }

        buttonSaveRecord.click {
            onSaveRecord()
        }
    }

    private fun initInputTime() {
        val currentTime = System.currentTimeMillis()
        val timeStr = convertLongToDateString(currentTime)
        binding.inputTime.setText(timeStr)
    }

    private fun observer() = viewModel.apply {
        childCat.observe(this@AddActualCostActivity) { list ->
            if (list.isEmpty() || list.filter { it.categoryParent.typeCategory == TYPE_EXPENSE }
                    .isEmpty()) {
                childCatAdapter.submitData(
                    mutableListOf(
                        ChildCategoryEntity(
                            -1L,
                            "Sample Data 1",
                            "",
                            ParentCategoryEntity(0, "Sample Data 1", R.drawable.icon_cate_bag)
                        ),
                    )
                )
            } else childCatAdapter.submitData(list.filter { it.categoryParent.typeCategory == TYPE_EXPENSE })
        }

        parentCat.observe(this@AddActualCostActivity) { list ->
            adapterParentCat.submitData(list.filter { it.typeCategory == TYPE_EXPENSE })
        }
    }

    private fun onSaveRecord() {
        val noteTitle = binding.inputNoteTitle.text.toString().trim()
        val revenueText = binding.inputRevenue.text.toString().trim()
        val time = System.currentTimeMillis()

        if (noteTitle.isEmpty() || revenueText.isEmpty() || childCatSelected == null) {
            showToast(R.string.this_field_cannot_be_left_blank)
        } else {
            viewModel.saveRecordActualCost(
                RecordActualCost(
                    noteTitle = noteTitle,
                    cost = revenueText.toLong(),
                    time = time,
                    categoryActualCost = childCatSelected!!
                ), this
            )

            // refresh
            binding.inputNoteTitle.text.clear()
            binding.inputRevenue.text.clear()
            childCatSelected = null
            val indexSelectedChildCatBefore = childCatAdapter.indexSelected
            childCatAdapter.indexSelected = -1
            childCatAdapter.notifyItemChanged(indexSelectedChildCatBefore)
            childCatSelected = null
        }
    }
}