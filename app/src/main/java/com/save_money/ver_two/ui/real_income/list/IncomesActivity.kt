package com.save_money.ver_two.ui.real_income.list

import android.graphics.Color
import androidx.activity.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.hide
import com.save_money.ver_two.commons.base.ext.show
import com.save_money.ver_two.database.entities.RecordRealIncome
import com.save_money.ver_two.databinding.ActivityIncomesBinding
import com.save_money.ver_two.ui.real_income.RealIncomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncomesActivity : BaseActivity<ActivityIncomesBinding>(R.layout.activity_incomes) {

    private val viewModel: RealIncomeViewModel by viewModels()
    private val realIncomeAdapter: RealIncomeAdapter by lazy {
        RealIncomeAdapter(this)
    }

    override fun initView() {
        clickViews()
        observeData()
        initAdapter()
    }

    private fun initAdapter() = binding.rcv.apply {
        adapter = realIncomeAdapter
    }

    private fun clickViews() = binding.apply {
        iconBack.click { finish() }
        iconFilter.click { onFilter() }
        textClearFilter.click {
            val isFilter = viewModel.isFilter.value ?: false
            if (isFilter) {
                realIncomeAdapter.submitData(
                    viewModel.listRecordRevenue.value ?: mutableListOf(
                        RecordRealIncome(
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
        listRecordRevenue.observe(this@IncomesActivity) { listRecord ->
            if (listRecord.isEmpty()) {
                val listTemp = mutableListOf(
                    RecordRealIncome(
                        0L, getString(R.string.record_sample_1), revenue = 123456,
                    )
                )
                realIncomeAdapter.submitData(listTemp)
            } else realIncomeAdapter.submitData(listRecord)
        }

        isFilter.observe(this@IncomesActivity) { filter ->
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
                viewModel.listRecordRevenue.value ?: mutableListOf(),
                onFinishFilter = { listAfterFilter ->
                    if (listAfterFilter.isEmpty()) {
                        binding.animNull.show()
                    } else {
                        binding.animNull.hide()
                    }
                    realIncomeAdapter.submitData(listAfterFilter)
                }
            )
        }
        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }
    }
}