package io.github.mushroom_cn.timemanager.jobs

import android.content.Context
import android.util.Log
import io.github.mushroom_cn.timemanager.data.local.entities.Configuration
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi

/**
 * 任务类
 */
class ScheduleJob @Inject constructor(private var configurationRepository: ConfigurationRepository) {
    var stopSubject = PublishSubject.create<Unit>()

    /**
     * 开启任务
     */
    fun start(): Observable<Configuration> {
        dispose()
        Log.d("ScheduleJob", "start run schedule task")
        stopSubject = PublishSubject.create<Unit>()
        val result = Observable.interval(20, TimeUnit.SECONDS).takeUntil(stopSubject)
            .scan(0L) { total, time -> total + time }
            .doOnNext { interval ->
                Log.d("ScheduleJob", "interval $interval start")
            }.switchMap {
                getFirstConfiguration(this.configurationRepository)
            }.map { config ->
                Log.d("ScheduleJob", "found: $config")
                onSchedule(config)
            }.doOnNext { config ->
                Log.d("ScheduleJob", "change to: $config")
            }.switchMap { config ->
                Log.d("ScheduleJob", "update schedule task config: $config")
                if (config.id < 1) {
                    configurationRepository.add(config).toObservable()
                } else {
                    configurationRepository.update(config).toObservable()
                }
            }.doOnError { error ->
                Log.e("ScheduleJob", "run schedule error: ${error.message.toString()}")
            }.filter { config ->
                config.remainTimeOfEachViewing < 1 || config.remainViewCount < 1
            }
        return result;
    }

    /**
     * 停止任务
     */
    fun dispose() {
        stopSubject.onNext(Unit)
    }

    /**
     * worker中弹窗
     */
    private fun scheduleWork(config: Configuration, context: Context) {
        // 1分钟后弹窗
//        val workRequest = OneTimeWorkRequestBuilder<ShowFloatingWindowWorker>().setInitialDelay(
//            1, TimeUnit.MINUTES
//        ).build()
//        WorkManager.getInstance(context).enqueue(workRequest)
//        Log.i(
//            "TimeManagerApplication",
//            context.getString(R.string.create_schedule_work, config.timeOfEachViewing)
//        )
    }
}

/**
 * 获取第一条记录
 */
fun getFirstConfiguration(configurationRepository: ConfigurationRepository): Observable<Configuration> {
    return configurationRepository.getAll().flatMap { configurations: List<Configuration> ->
        Log.d("ScheduleJob", "${configurations.size} found")
        Flowable.fromIterable(configurations).defaultIfEmpty(getDefaultConfiguration())
    }.take(1).toObservable()
}


/**
 * 执行调度任务。 本质上是在修改configuration
 */
private fun runScheduleTask(
    configurations: Observable<Configuration>
): Single<Configuration> {
    val defaultConfig = getDefaultConfiguration()
    // 取出第一个
    return configurations.map { it ?: defaultConfig }.map(::onSchedule).singleOrError()
}

/**
 * 任务真正的执行者
 */
private fun onSchedule(firstConfiguration: Configuration): Configuration {
    val now = LocalDateTime.now()
    val nowTimestamp = toUTCLong(now);
    // 下次时间是 间隔时间
    val nextStartDate = LocalDateTime.parse(firstConfiguration.lastModifyDateTime)
        .plusMinutes(firstConfiguration.minViewInterval.toLong());
    val nextStartDateTimestamp = toUTCLong(nextStartDate)
    // 今天次数用尽
    if (firstConfiguration.remainViewCount < 1) {
        // 兼容跨天逻辑, 不是同一天则把剩余的参数恢复
        if (nowTimestamp > nextStartDateTimestamp && !toISODateFormat(now).equals(
                toISODateFormat(nextStartDate)
            )
        ) {
            val newFirstConfiguration = firstConfiguration.copy(
                remainTimeOfEachViewing = firstConfiguration.timeOfEachViewing,
                remainViewCount = firstConfiguration.maxViewsCount,
            )
            Log.d("ScheduleJob", "reset config: $newFirstConfiguration")
            return newFirstConfiguration
        }
        val newFirstConfiguration = firstConfiguration.copy(timeOfEachViewing = 0)
        Log.d("ScheduleJob", "$newFirstConfiguration has no view time ")
        return newFirstConfiguration
    }
    if (firstConfiguration.remainTimeOfEachViewing < 1) {
        // 本次剩余时间为0
        if (nowTimestamp > nextStartDateTimestamp) {
            // 超过了等待时长， 即恢复剩余时间
            val newFirstConfiguration = firstConfiguration.copy(
                remainTimeOfEachViewing = firstConfiguration.timeOfEachViewing,
                lastModifyDateTime = toISODateTimeFormat(LocalDateTime.now())
            )
            Log.d("ScheduleJob", "recovery $newFirstConfiguration")
            return newFirstConfiguration
        }
        val newFirstConfiguration = firstConfiguration.copy(timeOfEachViewing = 0)
        Log.d("ScheduleJob", "$newFirstConfiguration is having a rest")
        // 等待观影休息中
        return newFirstConfiguration
    }

    // 仍然有观影机会，跟随job时间减小1
    // 时间<1时，剩余次数也要跟着变化，必须-1
    val newRemainTimeOfEachViewing = firstConfiguration.remainTimeOfEachViewing - 1
    val newRemainViewCount =
        if (newRemainTimeOfEachViewing < 1) firstConfiguration.remainViewCount - 1 else firstConfiguration.remainViewCount
    val lastModifyDateTime =
        if (newRemainTimeOfEachViewing < 1) toISODateTimeFormat(now) else firstConfiguration.lastModifyDateTime
    val newFirstConfiguration = firstConfiguration.copy(
        remainTimeOfEachViewing = newRemainTimeOfEachViewing,
        remainViewCount = newRemainViewCount,
        lastModifyDateTime = lastModifyDateTime
    )
    Log.d("ScheduleJob", "remain: $newFirstConfiguration")
    return newFirstConfiguration
}

/**
 * 获取默认的配置
 */
private fun getDefaultConfiguration(): Configuration {
    val defaultConfig = Configuration(
        createDateTime = toISODateTimeFormat(LocalDateTime.now()),
        lastModifyDateTime = toISODateTimeFormat(LocalDateTime.now()),
        maxViewsCount = 3, // 默认每天最大观看次数3次
        remainViewCount = 3,
        minViewInterval = 2 * 60, // 默认两次间隔 120分钟
        timeOfEachViewing = 30, // 默认每次观看时长30分钟
        remainTimeOfEachViewing = 30,
    )
    return defaultConfig;
}

private fun toUTCLong(date: LocalDateTime): Long {
    return date.toEpochSecond(ZoneOffset.UTC).toLong()
}

private fun toISODateTimeFormat(date: LocalDateTime): String {
    return date.format(DateTimeFormatter.ISO_DATE_TIME)
}

private fun toISODateFormat(date: LocalDateTime): String {
    return date.format(DateTimeFormatter.ISO_DATE)
}