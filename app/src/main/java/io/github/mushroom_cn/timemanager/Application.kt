package io.github.mushroom_cn.timemanager

import android.app.Application
import android.view.accessibility.AccessibilityManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.github.mushroom_cn.timemanager.jobs.ScheduleJob
import io.github.mushroom_cn.timemanager.receivers.createWorker
import io.github.mushroom_cn.timemanager.workers.BootWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class TimeManagerApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var configurationRepository: ConfigurationRepository

    @Inject
    lateinit var scheduleJob: ScheduleJob;

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
//
    @Inject
    lateinit var bootWorkerFactory: BootWorkerFactory

    private lateinit var accessibilityManager: AccessibilityManager
    override val workManagerConfiguration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(bootWorkerFactory)
            .build()


    @Inject
    override fun onCreate() {
        super.onCreate()
        configurationRepository.get(0)
        accessibilityManager = getSystemService(AccessibilityManager::class.java)
        createWorker(this);
    }


}
