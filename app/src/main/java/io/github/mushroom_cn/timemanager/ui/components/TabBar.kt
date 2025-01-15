package io.github.mushroom_cn.timemanager.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonColors
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Tab
import androidx.tv.material3.TabDefaults
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import io.github.mushroom_cn.timemanager.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TabBar() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf(R.string.bind_device, R.string.authorize_and_unlock)
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    interactionSource = interactionSource,
                    onFocus = {
                        Log.d("debug", "focus on me")
                        Log.d("debug", "focus on me")
                        Log.d("debug", "focus on me")
                    },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    },
                    modifier = Modifier
                        .focusable(interactionSource = interactionSource)
                        .onFocusEvent {
                            Log.d("debug", "focus on me ")
                            Log.d("debug", "focus on me ")
                            Log.d("debug", "focus on me ")
                        },
                    content = {
                        Text(
                            text = LocalContext.current.getString(title),
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> TabContent(
                """
        {
          "ip": "192.168.1.1",
          "port": 8080,
          "data": {
            "id": 123,
            "today": "2025-01-12T07:02:08.296Z",
            "durationOfEachViewing": 30,
            "maxViewsCount": 5,
            "remainViewCount": 4
          }
        }
    """
            ) // 绑定设备
            1 -> TabContent(
                """
        {
          "ip": "192.168.1.1",
          "port": 8080,
          "data": {
            "id": 123,
            "today": "2025-01-12T07:02:08.296Z",
            "durationOfEachViewing": 30,
            "maxViewsCount": 5,
            "remainViewCount": 4
          }
        }
    """
            ) // 授权解锁
        }
    }
}

@Composable
fun TabContent(qrCodeText: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // 水平居中对齐
        verticalArrangement = Arrangement.Center // 垂直居中排列
    ) {
        QRCodeScreen(qrCodeText)
        val currentZonedDateTime = ZonedDateTime.now();
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss")
        val formattedZonedDateTime = currentZonedDateTime.format(formatter)
        Text(
            text = LocalContext.current.getString(R.string.createTimeX, formattedZonedDateTime),
            fontSize = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTabBar() {
    TabBar()
}