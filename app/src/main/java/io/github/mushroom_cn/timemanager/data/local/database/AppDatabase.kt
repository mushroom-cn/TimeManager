package io.github.mushroom_cn.timemanager.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationDao
import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationHistoryDao
import io.github.mushroom_cn.timemanager.data.local.entities.Configuration
import io.github.mushroom_cn.timemanager.data.local.entities.ConfigurationHistory

/**
 * 参考文档 https://developer.android.com/training/data-storage/room?hl=zh-cn
 */
@Database(entities = [Configuration::class, ConfigurationHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configurationDao(): ConfigurationDao
    abstract fun configurationRecordsDao(): ConfigurationHistoryDao
}


//val db = Room.databaseBuilder(
//    applicationContext,
//    AppDatabase::class.java, "database-name"
//).build()