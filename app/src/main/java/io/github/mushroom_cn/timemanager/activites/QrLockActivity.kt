package io.github.mushroom_cn.timemanager.activites;

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import io.github.mushroom_cn.timemanager.ui.components.QRCodeScreen
import java.util.UUID

class QrLockActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 生成唯一token
        val unlockToken = UUID.randomUUID().toString()

        val callback = QrLockBackPressedCallback {
            finish()
        }
        onBackPressedDispatcher.addCallback(this, callback)
        setContent {
            QRCodeScreen(unlockToken)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

class QrLockBackPressedCallback(
    enabled: Boolean = true,
    private val onBackPressedAction: () -> Unit
) : OnBackPressedCallback(enabled) {

    override fun handleOnBackPressed() {
        onBackPressedAction()
    }
}