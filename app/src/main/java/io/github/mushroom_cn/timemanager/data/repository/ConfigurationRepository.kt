package io.github.mushroom_cn.timemanager.data.repository

import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationDao
import io.github.mushroom_cn.timemanager.data.local.entities.Configuration
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(private val dao: ConfigurationDao) {

    /**
     * 获取配置。 默认取第一个， 系统中只有一条记录
     */
    fun get(id: Long): Single<Configuration> {
        return dao.findById(id)
    }

    fun getAll(): Flowable<List<Configuration>> {
        return dao.getAll()
    }

    /**
     * 记录分页
     */
    fun get(limit: Int, offset: Int): Flowable<List<Configuration>> {
        return dao.get(limit, offset)
    }

    /**
     * 更新配置
     */
    fun update(config: Configuration): Single<Configuration> {
        dao.update(config);
        return dao.findById(config.id)
    }

    /**
     * 添加配置。 默认取第一个， 系统中只有一条记录
     */
    fun add(conf: Configuration): Single<Configuration> {
        return dao.insert(conf).toObservable().switchMap { id ->
            get(id).toObservable()
        }.singleOrError()
    }

    /**
     * 删除配置。 默认取第一个， 系统中只有一条记录
     */
    fun remove(id: Long): Single<Configuration> {
        val conf = dao.findById(id)
//        dao.delete(conf)
        return conf
    }
}