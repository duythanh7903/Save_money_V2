package com.save_money.ver_two.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.PreferencesUtils
import com.save_money.ver_two.commons.base.BaseFragment
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.convertJsonToObject
import com.save_money.ver_two.database.entities.Activities
import com.save_money.ver_two.database.entities.UserEntity
import com.save_money.ver_two.databinding.FragmentHomeBinding
import com.save_money.ver_two.ui.actual_cost.add.AddActualCostActivity
import com.save_money.ver_two.ui.est_cost.add.AddEstCostActivity
import com.save_money.ver_two.ui.exp_income.add.AddExpIncomeActivity
import com.save_money.ver_two.ui.notifications.NotificationsActivity
import com.save_money.ver_two.ui.real_income.add.AddRealIncomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val activitiesAdapter: ActivitiesAdapter by lazy {
        ActivitiesAdapter()
    }

    private lateinit var accountCurrent: UserEntity

    override fun initData() {
    }

    override fun initView() {
        autoTextSlide()
        clickViews()
        observer()
        initRcvActivities()
    }

    override fun onResume() {
        super.onResume()

        try {
            getAccountCurrent()
            showInformationUser()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showInformationUser() {
        if (this::accountCurrent.isInitialized) {
            val base64Avatar = accountCurrent.base64Image
            val userName = accountCurrent.userName

            Glide.with(requireActivity()).load(
                if (base64Avatar.isNotEmpty()) Uri.parse(base64Avatar)
                else R.drawable.avatar_default
            ).into(binding.avatar)
            binding.textHello.text = "${requireActivity().getString(R.string.hello)}, $userName"

            val accountBalance = PreferencesUtils.accountBalance
            binding.textAccountBalance.text = "$accountBalance VND"
        }
    }

    private fun getAccountCurrent() = activity?.let { act ->
        val jsonAccountCurrent = PreferencesUtils.jsonAccount
        accountCurrent =
            act.convertJsonToObject(jsonAccountCurrent, UserEntity::class.java)
    }

    private fun autoTextSlide() = binding.apply {
        text1.isSelected = true
        text2.isSelected = true
        text3.isSelected = true
        text4.isSelected = true
    }

    private fun clickViews() = binding.apply {
        btnRealIncome.click {
            startActivity(Intent(requireActivity(), AddRealIncomeActivity::class.java))
        }

        btnActualCost.click {
            startActivity(Intent(requireActivity(), AddActualCostActivity::class.java))
        }

        btnEstCost.click {
            startActivity(Intent(requireActivity(), AddEstCostActivity::class.java))
        }

        btnExpectedIncome.click {
            startActivity(Intent(requireActivity(), AddExpIncomeActivity::class.java))
        }

        iconNoti.click {
            startActivity(Intent(requireActivity(), NotificationsActivity::class.java))
        }
    }

    private fun observer() = viewModel.apply {
        listActivities.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                val listSampleData = mutableListOf(
                    Activities(
                        0,
                        requireActivity().getString(R.string.this_is_sample_item),
                        System.currentTimeMillis(),
                        123456789,
                        false
                    )
                )
                activitiesAdapter.submitData(listSampleData)
            } else activitiesAdapter.submitData(list)
        }
    }

    private fun initRcvActivities() = binding.rcvActivities.apply {
        adapter = activitiesAdapter
    }
}