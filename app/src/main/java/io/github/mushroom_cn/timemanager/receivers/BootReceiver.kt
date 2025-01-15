package io.github.mushroom_cn.timemanager.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.mushroom_cn.timemanager.R
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.github.mushroom_cn.timemanager.workers.BootWorker
import javax.inject.Inject


@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var userRepository: ConfigurationRepository
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // 在这里启动你的服务或活动
            Log.i("BootReceiver", context.getString(R.string.action_boot_completed))
            // 设备重启后，创建 WorkRequest
            createWorker(context);
        }
        if (intent.action == Intent.ACTION_SHUTDOWN) {
            // 关机
            Log.i("BootReceiver", context.getString(R.string.action_shutdown))
        }
    }
}


fun createWorker(context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<BootWorker>()
        .build()
    val worker = WorkManager.getInstance(context)
        .enqueueUniqueWork("uniqueWorkName", ExistingWorkPolicy.REPLACE, workRequest)
    val workerId = workRequest.id;
}