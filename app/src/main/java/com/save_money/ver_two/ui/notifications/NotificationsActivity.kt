package com.save_money.ver_two.ui.notifications

import androidx.activity.viewModels
import com.save_money.ver_two.R
import com.save_money.ver_two.commons.base.BaseActivity
import com.save_money.ver_two.commons.base.ext.click
import com.save_money.ver_two.commons.base.ext.showToast
import com.save_money.ver_two.database.entities.NotificationEntity
import com.save_money.ver_two.databinding.ActivityNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsActivity :
    BaseActivity<ActivityNotificationsBinding>(R.layout.activity_notifications) {

    private lateinit var notificationAdapter: NotificationAdapter
    private val viewModel: NotiViewModel by viewModels()

    override fun initView() {
        initRcv()
        observeData()
        clickViews()
    }

    private fun initRcv() = binding.rcv.apply {
        notificationAdapter = NotificationAdapter(this@NotificationsActivity)
        { noti, index ->
            if (noti.title == getString(R.string.notification_sample_1)) {
                showToast(R.string.this_is_sample_item)
            } else {
                if (!noti.isRead) viewModel.updateNoti(noti.apply {
                    isRead = true
                })
                notificationAdapter.notifyItemChanged(index)
            }
        }
        adapter = notificationAdapter
    }

    private fun observeData() = viewModel.apply {
        listNoti.observe(this@NotificationsActivity) { listNotification ->
            if (listNotification.isEmpty()) {
                val listTemp = mutableListOf(
                    NotificationEntity(
                        0L,
                        getString(R.string.notification_sample_1),
                        getString(R.string.content_sample_1),
                    )
                )
                notificationAdapter.submitData(listTemp)
            } else notificationAdapter.submitData(listNotification)
        }
    }

    private fun clickViews() = binding.apply {
        iconBack.click { finish() }

        textMarkAllDone.click { updateAllNoti() }
    }

    private fun updateAllNoti() {
        val listNotiIsNotRead =
            notificationAdapter.list.filter { !it.isRead }.toMutableList()
        listNotiIsNotRead.map { it.isRead = true }.toMutableList()
        viewModel.updateAllNoti(listNotiIsNotRead)
    }
}