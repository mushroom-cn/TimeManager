package io.github.mushroom_cn.timemanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.mushroom_cn.timemanager.data.local.entities.ConfigurationHistory

@Dao
interface ConfigurationHistoryDao {
    @Query("SELECT * FROM ConfigurationHistories ORDER BY id DESC")
    fun getAll(): List<ConfigurationHistory>

    @Query("SELECT * FROM ConfigurationHistories WHERE id IN (:userIds) ORDER BY id DESC")
    fun loadAllByIds(userIds: IntArray): List<ConfigurationHistory>

    @Query("SELECT * FROM ConfigurationHistories WHERE id = :id ORDER BY id DESC")
    fun findById(id: Long): ConfigurationHistory

    @Query("SELECT  * FROM ConfigurationHistories ORDER BY id DESC LIMIT :limit OFFSET :offset")
    fun get(limit: Int, offset: Int): List<ConfigurationHistory>

    @Insert
    fun insert(vararg users: ConfigurationHistory)

    @Delete
    fun delete(user: ConfigurationHistory)

    @Update()
    fun update(record: ConfigurationHistory)
}