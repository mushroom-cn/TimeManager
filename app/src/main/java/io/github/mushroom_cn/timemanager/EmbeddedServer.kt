package io.github.mushroom_cn.timemanager

import android.app.Application
import android.content.Intent
import io.github.mushroom_cn.timemanager.data.local.entities.Configuration
import io.github.mushroom_cn.timemanager.data.repository.ConfigurationRepository
import io.github.mushroom_cn.timemanager.receivers.Actions
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import javax.inject.Inject

class EmbeddedServer @Inject constructor(private val applicationContext: Application) {

    @Inject
    lateinit var configurationRepository: ConfigurationRepository
    private lateinit var server: io.ktor.server.engine.EmbeddedServer<NettyApplicationEngine, NettyApplicationEngine.Configuration>

    /**
     * @description 修改配置
     */
    fun onModifyConfig() {

    }

    /**
     * @description 获取配置
     */
    fun onGetConfig() {

    }

    /**
     * @description 手动上锁
     */
    fun onLock() {
        val intent = Intent(Actions.SHOW_FLOAT_WINDOW)
        applicationContext.sendBroadcast(intent)
    }

    /**
     * @description 手动解锁
     */
    fun onUnlock() {
        val intent = Intent(Actions.CLOSE_FLOAT_WINDOW)
        applicationContext.sendBroadcast(intent)
    }

    fun start() {
        server = embeddedServer(Netty, port = 8080) {
            install(ContentNegotiation) {
                json()
            }
            routing {
                post("/api/v1/config/modify") {
                    val body = call.receive<Configuration>()
                    var x = onModifyConfig();
                }

                post("/api/v1/config/get") {
                    var x = onGetConfig();
//                    call.respond(x)
                }

                get("/api/v1/lock") {
                    onLock();
                }

                get("/api/v1/unlock") {
                    onUnlock();
                }


            }
        }
        server.start(false)
    }

    fun stop() {
        server?.stop()
    }
}

@Serializable
data class ScanRequest(val code: String, val time: Long)