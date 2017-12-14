package com.cloudcoin.chat.raidachat.RAIDA

import com.cloudcoin.chat.raidachat.Extensions.debug
import com.google.gson.JsonObject
import com.neovisionaries.ws.client.*
import okio.ByteString
import org.jetbrains.anko.doAsync

/**
 * Created by infraymer on 29.11.17.
 */
class ConnectionManager(private val mWSListener: WSListener) {

    interface WSListener {
        fun onMessage(text: String)
    }

    /***********************************************************************************************
     * Properties
     ***********************************************************************************************/
    private val mWebSocketList = arrayListOf<WebSocket>()

    val isActioveCount: Int
        get() {
            var count = 0
            mWebSocketList.forEach { if (it.isOpen) count++ }
            return count
        }

    /***********************************************************************************************
     * Private funs
     ***********************************************************************************************/
    private fun createWebSocket(url: String): WebSocket {
        val ws = WebSocketFactory().createSocket(url)
        ws.addListener(MyWsListener())
        return ws
    }

    /***********************************************************************************************
     * Public funs
     ***********************************************************************************************/
    fun setWebSocketsList(listURL: ArrayList<String>) {
        listURL.forEach {
            mWebSocketList.add(createWebSocket(it))
        }
    }

    fun setWebSocketsList(vararg url: String) {
        url.forEach {
            mWebSocketList.add(createWebSocket(it))
        }
    }

    fun connect() {
        mWebSocketList.forEach {
            it.connectAsynchronously()
        }
    }

    fun send(jsonObject: JsonObject) {
        mWebSocketList.forEach { it.sendText(jsonObject.toString()) }
    }

    fun sed(requestsList: ArrayList<RaidaRequest>) {

    }

    /***********************************************************************************************
     * Listener class
     **********************************************************************************************/
    inner class MyWsListener : WebSocketAdapter() {

        override fun onError(websocket: WebSocket, cause: WebSocketException) {
            debug("onError")
        }

        override fun onConnected(websocket: WebSocket, headers: MutableMap<String, MutableList<String>>) {
            debug("onConnected: ${mWebSocketList.indexOf(websocket)}")
        }

        override fun onConnectError(websocket: WebSocket, exception: WebSocketException) {
            debug("onConnectError: ${mWebSocketList.indexOf(websocket)}")
        }

        override fun onDisconnected(websocket: WebSocket, serverCloseFrame: WebSocketFrame, clientCloseFrame: WebSocketFrame, closedByServer: Boolean) {
            debug("onDisconnected: ${mWebSocketList.indexOf(websocket)}")
        }

        override fun onTextMessage(websocket: WebSocket, text: String) {
            debug("onTextMessage: ${mWebSocketList.indexOf(websocket)}")
            mWSListener.onMessage(text)
        }
    }
}