package io.github.mushroom_cn.timemanager.util

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import io.github.mushroom_cn.timemanager.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

fun showScreenLock(fragment: FragmentActivity, executor: Executor, context: Context) {
    // 创建 BiometricPrompt 实例
    val biometricPrompt =
        BiometricPrompt(fragment, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // 用户成功输入了 PIN 或密码
                // 处理成功的情况
                handleAuthenticationSuccess();
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // 处理错误情况
                handleAuthenticationError("$errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // 处理身份验证失败的情况
            }
        })

    // 创建提示信息
    val promptInfo =
        BiometricPrompt.PromptInfo.Builder().setTitle(context.getString(R.string.pin_auth))
            .setSubtitle(context.getString(R.string.pin_placeholder))
            .setNegativeButtonText(context.getString(R.string.cancel)).build()
    // 调出身份验证界面
    biometricPrompt.authenticate(promptInfo)
}

fun handleAuthenticationSuccess() {
    // 处理成功的情况
    CoroutineScope(Dispatchers.Main).launch {
        // 在这里执行需要在主线程上运行的代码
        // 例如，更新 UI
    }
}

fun handleAuthenticationError(errorMsg: String) {
    // 处理错误情况
    CoroutineScope(Dispatchers.Main).launch {
        // 在这里执行需要在主线程上运行的代码
        // 例如，显示错误消息
        Log.d("MainActivity", errorMsg)
    }
}


fun isBiometricAvailable(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            // 生物识别可用
            true
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            // 设备没有生物识别硬件
            false
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            // 生物识别硬件不可用
            false
        }

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
            // 用户没有注册任何生物识别凭据
            false
        }

        else -> {
            // 其他错误
            false
        }
    }
}