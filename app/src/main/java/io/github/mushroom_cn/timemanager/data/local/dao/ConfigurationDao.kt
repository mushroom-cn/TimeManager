package io.github.mushroom_cn.timemanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.mushroom_cn.timemanager.data.local.entities.Configuration

/**
 * 参考文档 https://developer.android.com/training/data-storage/room?hl=zh-cn
 */
@Dao
interface ConfigurationDao {
    @Query("SELECT * FROM configurations ORDER BY id DESC")
    fun getAll(): List<Configuration>

    @Query("SELECT * FROM configurations WHERE id IN (:userIds) ORDER BY id DESC")
    fun loadAllByIds(userIds: IntArray): List<Configuration>

    @Query("SELECT * FROM configurations WHERE id = :id ORDER BY id DESC")
    fun findById(id: Long): Configuration

    @Query("SELECT * FROM configurations ORDER BY id DESC LIMIT :limit OFFSET :offset")
    fun get(limit: Int, offset: Int): List<Configuration>

    @Insert
    fun insert(vararg user: Configuration)

    @Delete
    fun delete(user: Configuration)

    @Update()
    fun update(configuration: Configuration)

}