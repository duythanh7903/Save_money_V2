package com.save_money.ver_two.ui.main.chart

import android.graphics.Color
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseFragment
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.hide
import com.save_money.ver_two.commons.base.ext.show
import com.save_money.ver_two.databinding.FragmentChartBinding
import com.save_money.ver_two.ui.main.chart.ChartViewModel.Companion.INDEX_TAB_LAST30
import com.save_money.ver_two.ui.main.chart.ChartViewModel.Companion.INDEX_TAB_LAST7
import com.save_money.ver_two.ui.main.chart.ChartViewModel.Companion.INDEX_TAB_TODAY


class ChartFragment : BaseFragment<FragmentChartBinding>(R.layout.fragment_chart) {

    private val viewModel: ChartViewModel by viewModels()
    private val listTextTab: MutableList<TextView> by lazy {
        mutableListOf(
            binding.tabToday,
            binding.tabLast7Days,
            binding.tabLast30Days,
        )
    }
    private val modeAdapter: ModeAdapter by lazy {
        ModeAdapter { mode, _ ->
            binding.viewTurnOffRcv.performClick()
            binding.textMode.text = mode
        }.apply {
            submitData(
                mutableListOf(
                    getString(R.string.real_income),
                    getString(R.string.actual_cost),
                    getString(R.string.expected_income),
                    getString(R.string.estimated_cost),
                )
            )
        }
    }

    override fun initData() = Unit

    override fun initView() {
        viewModel.apply {
            indexTab.observe(viewLifecycleOwner) { index ->
                listTextTab.forEach { item ->
                    item.isActivated = false
                    item.setTextColor(Color.parseColor("#FF1B2251"))
                }
                listTextTab[index].isActivated = true
                listTextTab[index].setTextColor(Color.parseColor("#FFFFFF"))
            }
        }

        binding.apply {
            tabToday.click { viewModel.changeTab(INDEX_TAB_TODAY) }
            tabLast7Days.click { viewModel.changeTab(INDEX_TAB_LAST7) }
            tabLast30Days.click { viewModel.changeTab(INDEX_TAB_LAST30) }

            btnChooseMode.click {
                val rcvAlreadyTurnOn = iconUp.isActivated
                if (!rcvAlreadyTurnOn) {
                    containerRcv.show()
                    iconUp.isActivated = true
                    viewTurnOffRcv.show()
                }
            }

            viewTurnOffRcv.click {
                containerRcv.hide()
                it?.hide()
                iconUp.isActivated = false
            }

            rcvMoode.adapter = modeAdapter

            textMode.text = modeAdapter.list[0]

            textMode.isSelected = true
        }

        initChart()
    }

    private fun initChart() {
        val anyChartView: AnyChartView = binding.chart
        anyChartView.setProgressBar(binding.progressBar)

        val cartesian: Cartesian = AnyChart.column()

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("1", 80540))
        data.add(ValueDataEntry("2", 94190))
        data.add(ValueDataEntry("3", 102610))
        data.add(ValueDataEntry("4", 110430))
        data.add(ValueDataEntry("5", 128000))
        data.add(ValueDataEntry("6", 143760))
        data.add(ValueDataEntry("7", 170670))
        data.add(ValueDataEntry("8", 213210))
        data.add(ValueDataEntry("9", 249980))
        data.add(ValueDataEntry("10", 80540))
        data.add(ValueDataEntry("11", 128000))
        data.add(ValueDataEntry("12", 94190))

        val column: Column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("\${%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.title("")

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("\${%Value}{groupsSeparator: }")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("")
        cartesian.yAxis(0).title("")

        anyChartView.setChart(cartesian)
    }


}