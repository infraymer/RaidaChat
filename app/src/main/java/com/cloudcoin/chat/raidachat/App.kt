package com.cloudcoin.chat.raidachat

import android.annotation.SuppressLint
import android.app.Application
import com.cloudcoin.chat.raidachat.Extensions.debug
import com.cloudcoin.chat.raidachat.RAIDA.RAIDA
import com.neovisionaries.ws.client.*
import org.jetbrains.anko.doAsync
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * Created by infraymer on 05.12.17.
 */
class App : Application() {

    companion object {
        lateinit var INSTANCE: App
            private set
    }

    private lateinit var mCicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        mCicerone = Cicerone.create()

    }

    fun getNavigatorHolder() = mCicerone.navigatorHolder

    fun getRouter() = mCicerone.router

}