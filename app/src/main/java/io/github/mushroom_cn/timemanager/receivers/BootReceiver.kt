package io.github.mushroom_cn.timemanager.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import io.github.mushroom_cn.timemanager.R
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.github.mushroom_cn.timemanager.getNextLockTimeMillis
import io.github.mushroom_cn.timemanager.isAlarmSet
import io.github.mushroom_cn.timemanager.setLockScreenAlarm
import javax.inject.Inject


@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var userRepository: ConfigurationRepository
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // 启动服务活动
            Log.i("BootReceiver", context.getString(R.string.action_boot_completed))
            // 设备重启后，创建 WorkRequest
            val triggerAtMillis = getNextLockTimeMillis(1) // 例如每天21:00
            if (!isAlarmSet(context)) {
                setLockScreenAlarm(context, triggerAtMillis)
            }
        }
        if (intent.action == Intent.ACTION_SHUTDOWN) {
            // 关机
            Log.i("BootReceiver", context.getString(R.string.action_shutdown))
        }
    }
}

