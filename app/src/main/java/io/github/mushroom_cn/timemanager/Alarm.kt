package io.github.mushroom_cn.timemanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import io.github.mushroom_cn.timemanager.receivers.LockScreenReceiver
import org.ocpsoft.prettytime.PrettyTime
import java.util.Date

/**
 * @description 检查定时任务是否已经开启
 */
fun isAlarmSet(context: Context): Boolean {
    val intent = Intent(context, LockScreenReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
    )
    return pendingIntent != null
}

/**
 * @description  获取下一次定时任务的时间毫秒值
 */
fun getNextLockTimeMillis(minutesFromNow: Int): Long {
    val now = System.currentTimeMillis()
    val millisToAdd = minutesFromNow * 60 * 1000L
    return now + millisToAdd
}

/**
 * @description 设置定时任务
 */
fun setLockScreenAlarm(context: Context, triggerAtMillis: Long) {
   val intent = Intent(context, LockScreenReceiver::class.java).apply {
        action = "io.github.mushroom_cn.timemanager.LOCK_SCREEN_ALARM"
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerAtMillis,
        pendingIntent
    )
    val prettyTime = PrettyTime()
    Log.d("setLockScreenAlarm", "set alarm task, and task will start on " + prettyTime.format(Date(triggerAtMillis)))
}