package com.cloudcoin.chat.raidachat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val URL_API_1 = "ws://5.141.98.216:49011"
        const val URL_API_2 = "ws://5.141.98.216:49012"

        const val NORMAL_CLOSURE_STATUS = 1000
    }

    private lateinit var mConnectionManager: ConnectionManager

    /***********************************************************************************************
    * Override funs
    ***********************************************************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            button("run") {
                setOnClickListener { start() }
            }
        }
        mConnectionManager = ConnectionManager()
    }

    /***********************************************************************************************
    * Private funs
    ***********************************************************************************************/
    private fun start() {
        mConnectionManager.addWebSocket(URL_API_1)
        mConnectionManager.addWebSocket(URL_API_2)
    }
}
