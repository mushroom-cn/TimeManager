package io.github.mushroom_cn.timemanager.data

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationDao
import io.github.mushroom_cn.timemanager.data.local.database.AppDatabase
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
            Log.d("SQL", "$sqlQuery. SQL Args: $bindArgs")
        }, Executors.newSingleThreadExecutor()).build()
    }

    @Provides
    @Singleton
    fun provideConfigurationDao(database: AppDatabase): ConfigurationDao {
        return database.configurationDao()
    }

    @Provides
    @Singleton
    fun provideConfigurationRepository(userDao: ConfigurationDao): ConfigurationRepository {
        return ConfigurationRepository(userDao)
    }

}