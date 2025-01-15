package io.github.mushroom_cn.timemanager.data.remote.websocket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.github.mushroom_cn.timemanager.data.remote.websoket.module
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WebSocketService : Service() {

    override fun onCreate() {
        super.onCreate()
        startWebSocketServer()
    }

    private fun startWebSocketServer() {
        CoroutineScope(Dispatchers.IO).launch {
            embeddedServer(Netty, port = 8443, module = Application::module).start(wait = true)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
//        webSocketServer.stop();
    }
}