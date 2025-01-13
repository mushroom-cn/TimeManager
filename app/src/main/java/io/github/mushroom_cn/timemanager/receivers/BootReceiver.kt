package io.github.mushroom_cn.timemanager.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import io.github.mushroom_cn.timemanager.workers.BootWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // 在这里启动你的服务或活动
            Log.d("BootReceiver", "设备启动完成，执行相应操作")
            // 创建一个 WorkRequest
            val workRequest = OneTimeWorkRequest.Builder(BootWorker::class.java).build()
            // 将 WorkRequest 提交给 WorkManager
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}