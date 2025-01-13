package io.github.mushroom_cn.timemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.room.Room
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import dagger.hilt.android.AndroidEntryPoint
import io.github.mushroom_cn.timemanager.data.local.database.AppDatabase
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.github.mushroom_cn.timemanager.ui.components.TabBar
import io.github.mushroom_cn.timemanager.ui.theme.TimeManagerTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userRepository: ConfigurationRepository
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "timemanager.db"
        ).build()
        setContent {
            TimeManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    TabBar()
                }
            }
        }
    }
}

