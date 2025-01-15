package io.github.mushroom_cn.timemanager.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import io.github.mushroom_cn.timemanager.R

class FloatingWindowService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var floatingView: View

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

//        // 创建悬浮窗视图
//        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_window, null)
//
//        // 设置悬浮窗参数
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.WRAP_CONTENT,
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Android 8.0 及以上
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSLUCENT
//        )
//
//        // 设置悬浮窗位置
//        params.gravity = Gravity.TOP or Gravity.START
//        params.x = 0
//        params.y = 100

        // 添加悬浮窗视图
//        windowManager.addView(floatingView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除悬浮窗
        if (::floatingView.isInitialized) {
            windowManager.removeView(floatingView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}