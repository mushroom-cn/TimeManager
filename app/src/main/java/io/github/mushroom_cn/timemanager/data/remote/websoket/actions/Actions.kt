package io.github.mushroom_cn.timemanager.data.remote.websoket.actions


data class BindDevicePayload(val type: String, val payload: String) {

}


data class Action<T>(val type:String, val payload: T);


