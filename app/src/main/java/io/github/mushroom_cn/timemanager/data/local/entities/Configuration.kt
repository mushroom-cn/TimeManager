package io.github.mushroom_cn.timemanager.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "configurations")
data class Configuration(
    /**
     * @description 记录ID，
     * @readonly
     */
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo()
    val createDate: Long,

    @ColumnInfo()
    val modifyDate: Long,
    /**,
     * @description 服务器带时区时间
     * @readonly
     */
    @ColumnInfo()
    var today: String,
    /**
     * @description 本次观看剩余时长
     * @readonly
     */
    var remainDuration: Int,
    /**
     * @description 规则是否启用
     */
    @ColumnInfo()
    var status: Boolean,
    /**
     * @description 每次观看时长 1-60
     */
    @ColumnInfo()
    var durationOfEachViewing: Int,
    /**
     * @description 每天最大开几次
     */
    @ColumnInfo()
    var maxViewsCount: Int,

    /**
     * @description 今天剩余观影次数
     */
    @ColumnInfo()
    var remainViewCount: Int,
    /**
     * @description 两次观影间隔最小时间
     */
    @ColumnInfo()
    var minViewInterval: Int,
)