package io.github.mushroom_cn.timemanager.data.repository

import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationHistoryDao
import io.github.mushroom_cn.timemanager.data.local.entities.ConfigurationHistory

class ConfigurationHistoryRepository(private val dao: ConfigurationHistoryDao) {

    /**
     * 获取配置。 默认取第一个， 系统中只有一条记录
     */
    fun get(id: Long): ConfigurationHistory {
        return dao.findById(id)
    }

    /**
     * 记录分页
     */
    fun get(limit: Int, offset: Int): Collection<ConfigurationHistory> {
        return dao.get(limit, offset)
    }

    /**
     * 添加配置修改记录。
     */
    fun add(conf: ConfigurationHistory): ConfigurationHistory {
        val newId = dao.insert(conf)
        return dao.findById(0)
    }

    /**
     * 配置修改记录
     */
    fun remove(id: Long): ConfigurationHistory {
        val conf = dao.findById(id)
        dao.delete(conf)
        return conf
    }
}