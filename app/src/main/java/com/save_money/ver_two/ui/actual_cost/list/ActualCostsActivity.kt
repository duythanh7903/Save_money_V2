package com.save_money.ver_two.ui.actual_cost.list

import android.graphics.Color
import androidx.activity.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.hide
import com.save_money.ver_two.commons.base.ext.show
import com.save_money.ver_two.database.entities.RecordActualCost
import com.save_money.ver_two.databinding.ActvityActualCostsBinding
import com.save_money.ver_two.ui.actual_cost.ActualCostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActualCostsActivity :
    BaseActivity<ActvityActualCostsBinding>(R.layout.actvity_actual_costs) {

    private val viewModel: ActualCostViewModel by viewModels()
    private val actualCostAdapter: ActualCostAdapter by lazy {
        ActualCostAdapter(this)
    }

    override fun initView() {
        clickViews()
        observeData()
        initAdapter()
    }

    private fun initAdapter() = binding.rcv.apply {
        adapter = actualCostAdapter
    }

    private fun clickViews() = binding.apply {
        iconBack.click { finish() }
        iconFilter.click { onFilter() }
        textClearFilter.click {
            val isFilter = viewModel.isFilter.value ?: false
            if (isFilter) {
                actualCostAdapter.submitData(
                    viewModel.listCost.value ?: mutableListOf(
                        RecordActualCost(
                            0L, getString(R.string.record_sample_1), cost = 123456,
                        )
                    )
                )
            }
            viewModel.clearFilter()
            binding.animNull.hide()
        }
    }

    private fun observeData() = viewModel.apply {
        listCost.observe(this@ActualCostsActivity) { listRecord ->
            if (listRecord.isEmpty()) {
                val listTemp = mutableListOf(
                    RecordActualCost(
                        0L, getString(R.string.record_sample_1), cost = 123456,
                    )
                )
                actualCostAdapter.submitData(listTemp)
            } else actualCostAdapter.submitData(listRecord)
        }

        isFilter.observe(this@ActualCostsActivity) { filter ->
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
                viewModel.listCost.value ?: mutableListOf(),
                onFinishFilter = { listAfterFilter ->
                    if (listAfterFilter.isEmpty()) {
                        binding.animNull.show()
                    } else {
                        binding.animNull.hide()
                    }
                    actualCostAdapter.submitData(listAfterFilter)
                }
            )
        }
        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }
    }
}