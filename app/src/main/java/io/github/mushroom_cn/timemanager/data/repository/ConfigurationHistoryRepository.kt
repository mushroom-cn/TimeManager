package io.github.mushroom_cn.timemanager.data.repository

import android.annotation.SuppressLint
import io.github.mushroom_cn.timemanager.data.local.dao.ConfigurationHistoryDao
import io.github.mushroom_cn.timemanager.data.local.entities.ConfigurationHistory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ConfigurationHistoryRepository(private val dao: ConfigurationHistoryDao) {

    /**
     * 获取配置。 默认取第一个， 系统中只有一条记录
     */
    fun get(id: Long): Single<ConfigurationHistory> {
        return dao.findById(id)
    }

    /**
     * 记录分页
     */
    fun get(limit: Int, offset: Int): Flowable<List<ConfigurationHistory>> {
        return dao.get(limit, offset)
    }

    /**
     * 添加配置修改记录。
     */
    fun add(conf: ConfigurationHistory): Single<ConfigurationHistory> {
        val newId = dao.insert(conf)
        return dao.findById(0)
    }

    /**
     * 配置修改记录
     */
    fun remove(id: Long) {
        dao.findById(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe() { conf ->
                dao.delete(conf)
            }
    }
}