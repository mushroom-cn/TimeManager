package io.github.mushroom_cn.timemanager.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
@Entity(tableName = "configurations")
data class Configuration(
    /**
     * 记录ID，
     * @readonly
     */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * 创建时间
     */
    @ColumnInfo()
    val createDateTime: String?,

    /**
     * 最后一次修改时间。 只上一次remainTimeOfEachViewing = 0 之后更新的时间
     */
    @ColumnInfo()
    val lastModifyDateTime: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),

    /**
     * 每次观看时长 1-60 单位分钟
     */
    @ColumnInfo()
    var timeOfEachViewing: Int,

    /**
     * 剩余时长，单位分钟。 这里需要考虑跳过休眠或关机时长
     */
    @ColumnInfo()
    var remainTimeOfEachViewing: Int,

    /**
     * 每天最大开几次
     */
    @ColumnInfo()
    var maxViewsCount: Int,

    /**
     * 今天剩余观影次数。 过了凌晨 00:00 就会恢复。
     */
    @ColumnInfo()
    var remainViewCount: Int,

    /**
     * 两次观影间隔最小时间. 单位分钟
     */
    @ColumnInfo()
    var minViewInterval: Int,

    )