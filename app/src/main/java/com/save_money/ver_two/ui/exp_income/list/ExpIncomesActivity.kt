package com.save_money.ver_two.ui.exp_income.list

import android.graphics.Color
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.hide
import com.save_money.ver_two.commons.base.ext.show
import com.save_money.ver_two.database.entities.RecordExpectedIncome
import com.save_money.ver_two.databinding.ActivityExpIncomesBinding
import com.save_money.ver_two.ui.exp_income.ExpIncomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpIncomesActivity : BaseActivity<ActivityExpIncomesBinding>(R.layout.activity_exp_incomes) {

    private val viewModel: ExpIncomeViewModel by viewModels()
    private val expIncomeAdapter: ExpectedIncomeAdapter by lazy {
        ExpectedIncomeAdapter(this)
    }

    override fun initView() {
        clickViews()
        observeData()
        initAdapter()
    }

    private fun initAdapter() = binding.rcv.apply {
        adapter = expIncomeAdapter
    }

    private fun clickViews() = binding.apply {
        iconBack.click { finish() }
        iconFilter.click { onFilter() }
        textClearFilter.click {
            val isFilter = viewModel.isFilter.value ?: false
            if (isFilter) {
                expIncomeAdapter.submitData(
                    viewModel.listRecordsExp.value ?: mutableListOf(
                        RecordExpectedIncome(
                            0L, getString(R.string.record_sample_1), revenue = 123456,
                        )
                    )
                )
            }
            viewModel.clearFilter()
            binding.animNull.hide()
        }
    }

    private fun observeData() = viewModel.apply {
        listRecordsExp.observe(this@ExpIncomesActivity) { listRecord ->
            if (listRecord.isEmpty()) {
                val listTemp = mutableListOf(
                    RecordExpectedIncome(
                        0L, getString(R.string.record_sample_1), revenue = 123456,
                    )
                )
                expIncomeAdapter.submitData(listTemp)
            } else expIncomeAdapter.submitData(listRecord)
        }

        isFilter.observe(this@ExpIncomesActivity) { filter ->
            binding.textClearFilter.setTextColor(
                Color.parseColor(if (filter) "#FF1B2251" else "#C1C1C1")
            )
        }
    }

    private fun onFilter() {
        val picker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText(R.string.title_range_date)
                .build()
        picker.show(supportFragmentManager, "SAVE_MONEY")
        picker.addOnPositiveButtonClickListener {
            val startDateLongType = it.first
            val endDateLongType = it.second

            viewModel.handleFilterRecordByDate(
                startDateLongType, endDateLongType,
                viewModel.listRecordsExp.value ?: mutableListOf(),
                onFinishFilter = { listAfterFilter ->
                    if (listAfterFilter.isEmpty()) {
                        binding.animNull.show()
                    } else {
                        binding.animNull.hide()
                    }
                    expIncomeAdapter.submitData(listAfterFilter)
                }
            )
        }
        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }
    }
}