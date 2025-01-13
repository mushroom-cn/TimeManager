package io.github.mushroom_cn.timemanager.data.repository

import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationDao
import io.github.mushroom_cn.timemanager.data.local.entities.Configuration
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(private val dao: ConfigurationDao) {

    /**
     * 获取配置。 默认取第一个， 系统中只有一条记录
     */
    fun get(id: Long): Configuration {
        return dao.findById(id)
    }

    /**
     * 记录分页
     */
    fun get(limit: Int, offset: Int): Collection<Configuration> {
        return dao.get(limit, offset)
    }

    /**
     * 更新配置
     */
    fun update(config: Configuration): Configuration {
        dao.update(config);
        return dao.findById(config.id)
    }

    /**
     * 添加配置。 默认取第一个， 系统中只有一条记录
     */
    fun add(conf: Configuration): Configuration {
        val newId = dao.insert(conf)
        return dao.findById(0)
    }

    /**
     * 删除配置。 默认取第一个， 系统中只有一条记录
     */
    fun remove(id: Long): Configuration {
        val conf = dao.findById(id)
        dao.delete(conf)
        return conf
    }
}