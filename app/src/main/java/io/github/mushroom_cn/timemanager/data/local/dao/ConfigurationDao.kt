package io.github.mushroom_cn.timemanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.mushroom_cn.timemanager.data.local.entities.Configuration
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

/**
 * 参考文档 https://developer.android.com/training/data-storage/room?hl=zh-cn
 */
@Dao
interface ConfigurationDao {
    @Query("SELECT * FROM configurations ORDER BY id DESC")
    fun getAll(): Flowable<List<Configuration>>

    @Query("SELECT * FROM configurations WHERE id IN (:userIds) ORDER BY id DESC")
    fun loadAllByIds(userIds: IntArray): Flowable<List<Configuration>>

    @Query("SELECT * FROM configurations WHERE id = :id ORDER BY id DESC")
    fun findById(id: Long): Single<Configuration>

    @Query("SELECT * FROM configurations ORDER BY id DESC LIMIT :limit OFFSET :offset")
    fun get(limit: Int, offset: Int): Flowable<List<Configuration>>

    @Insert
    fun insert(config: Configuration): Single<Long>

    @Delete
    fun delete(config: Configuration)

    @Update()
    fun update(configuration: Configuration)

}