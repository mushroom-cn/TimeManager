package io.github.mushroom_cn.timemanager.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class BootWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        // 在这里执行你的任务
        // 例如，启动服务或执行其他操作
        return Result.success()
    }
}