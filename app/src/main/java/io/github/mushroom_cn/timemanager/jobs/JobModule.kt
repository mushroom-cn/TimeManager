package io.github.mushroom_cn.timemanager.jobs

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JobModule {

    @Provides
    @Singleton
    fun provideScheduleJob(configurationRepository: ConfigurationRepository): ScheduleJob {
        return ScheduleJob(configurationRepository);
    }
}