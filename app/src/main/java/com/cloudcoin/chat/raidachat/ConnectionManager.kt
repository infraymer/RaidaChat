package com.cloudcoin.chat.raidachat

import com.cloudcoin.chat.raidachat.Extensions.debug
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * Created by infraymer on 29.11.17.
 */
class ConnectionManager {

    private var mClient = OkHttpClient()
    private val mWebSocketList = arrayListOf<WebSocket>()

    init {
        mClient = OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build()
    }

    fun addWebSocket(url: String) {
        val client = OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build()
        val request = Request.Builder().url(url).build()
        val listener = MyListener()
        mWebSocketList.add(client.newWebSocket(request, listener))
        client.dispatcher().executorService().shutdown()
    }

    class MyListener: WebSocketListener() {
        override fun onOpen(webSocket: WebSocket?, response: Response?) {
            debug("$webSocket onOpen")
        }

        override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
            debug("$webSocket onFailure")
        }

        override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
            debug("$webSocket onClosing")
        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            debug("$webSocket onMessage")
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
            debug("$webSocket onMessage")
        }

        override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
            debug("$webSocket onClosed")
        }
    }
}