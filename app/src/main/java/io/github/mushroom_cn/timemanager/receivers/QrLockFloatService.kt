package io.github.mushroom_cn.timemanager.receivers

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import io.github.mushroom_cn.timemanager.ui.components.QRCodeScreen

class QrLockFloatService : Service() {
    private var windowManager: WindowManager? = null
    private var composeView: ComposeView? = null
    private val lifecycleOwner = ServiceLifecycleOwner()
    override fun onBind(intent: Intent?): IBinder? = null
    private val closeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                windowManager?.removeViewImmediate(composeView)
            } catch (e: Exception) {
                Log.e("QrLockFloatService", "closeReceiver", e)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(closeReceiver, IntentFilter(Actions.CLOSE_FLOAT_WINDOW))
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        // 创建 ComposeView
        composeView = ComposeView(this).apply {
            setViewTreeLifecycleOwner(lifecycleOwner)
            setViewTreeSavedStateRegistryOwner(lifecycleOwner)
            setContent {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue)
                )
                QRCodeScreen(
                    "21323"
                )
            }
        }
//        composeView?.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        windowManager?.addView(composeView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(closeReceiver)
        if (composeView != null) {
            windowManager?.removeView(composeView)
            composeView = null
        }
    }
}