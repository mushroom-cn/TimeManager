package io.github.mushroom_cn.timemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ConfigurationHistories")
data class ConfigurationHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long)
