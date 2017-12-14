package com.cloudcoin.chat.raidachat.Mvp.Presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.cloudcoin.chat.raidachat.App
import com.cloudcoin.chat.raidachat.Screens

/**
 * Created by infraymer on 05.12.17.
 */
@InjectViewState
class MainPresenter : MvpPresenter<MvpView>() {

    init {
        App.INSTANCE.getRouter().replaceScreen(Screens.LOGIN)
    }
}