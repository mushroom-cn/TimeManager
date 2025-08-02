package io.github.mushroom_cn.timemanager

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import javax.inject.Inject

@HiltAndroidApp
class TimeManagerApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var configurationRepository: ConfigurationRepository


    @Inject
    lateinit var embeddedServer: EmbeddedServer

    override val workManagerConfiguration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

    @Inject
    override fun onCreate() {
        super.onCreate()
        embeddedServer.start()
    }

    override fun onTerminate() {
        super.onTerminate()
        embeddedServer.stop()
    }

}
