package io.github.mushroom_cn.timemanager.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.WorkerParameters
import androidx.work.rxjava3.RxWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.github.mushroom_cn.timemanager.jobs.ScheduleJob
import io.reactivex.rxjava3.core.Single

@HiltWorker
class BootWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private var scheduleJob: ScheduleJob,
) : RxWorker(context, workerParams) {
    override fun createWork(): Single<Result> {
        return scheduleJob.start().map {
            Result.success()
        }.single(Result.success())
    }

}