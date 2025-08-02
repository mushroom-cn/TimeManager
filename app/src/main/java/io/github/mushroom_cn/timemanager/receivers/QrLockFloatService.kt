package io.github.mushroom_cn.timemanager.receivers

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import io.github.mushroom_cn.timemanager.ui.components.QRCodeScreen

class QrLockFloatService : Service() {
    private var windowManager: WindowManager? = null
    private var composeView: ComposeView? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // 创建 ComposeView
        composeView = ComposeView(this).apply {
            setContent {
                QRCodeScreen(
                    "21323"
                )
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        windowManager?.addView(composeView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (composeView != null) {
            windowManager?.removeView(composeView)
            composeView = null
        }
    }
}