package io.github.mushroom_cn.timemanager.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.github.mushroom_cn.timemanager.jobs.ScheduleJob
import javax.inject.Inject

class BootWorkerFactory @Inject constructor() : WorkerFactory() {
    @Inject
    lateinit var scheduleJob: ScheduleJob;

    @Inject
    lateinit var configurationRepository: ConfigurationRepository

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return BootWorker(appContext, workerParameters, scheduleJob);
    }
}