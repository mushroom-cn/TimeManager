package io.github.mushroom_cn.timemanager

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.github.mushroom_cn.timemanager.ui.components.TabBar
import io.github.mushroom_cn.timemanager.ui.theme.TimeManagerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userRepository: ConfigurationRepository

    private lateinit var confirmCredentialLauncher: ActivityResultLauncher<Intent>

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + this.packageName))
            this.startActivity(intent)
            // 提示用户授权
            return
        }
        var x = getNextLockTimeMillis(1);
        setLockScreenAlarm(this, x)
        setContent {
            TimeManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), shape = RectangleShape
                ) {
                    TabBar()
                }
            }
        }
    }

}

