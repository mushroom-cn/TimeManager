package io.github.mushroom_cn.timemanager.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationDao
import io.github.mushroom_cn.timemanager.data.local.database.AppDatabase
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import javax.inject.Singleton
import android.app.Application
import androidx.room.Room

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): ConfigurationDao {
        return database.configurationDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: ConfigurationDao): ConfigurationRepository {
        return ConfigurationRepository(userDao)
    }
}