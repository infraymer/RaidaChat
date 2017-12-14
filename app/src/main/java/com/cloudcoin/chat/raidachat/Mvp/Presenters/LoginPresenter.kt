package com.cloudcoin.chat.raidachat.Mvp.Presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.cloudcoin.chat.raidachat.App
import com.cloudcoin.chat.raidachat.RAIDA.RAIDA
import com.cloudcoin.chat.raidachat.Screens
import org.jetbrains.anko.doAsync

/**
 * Created by infraymer on 05.12.17.
 */
@InjectViewState
class LoginPresenter : MvpPresenter<MvpView>() {

    fun onSignInButtonClicked(login: String, password: String) {
        RAIDA.authorization(login, password)
//        App.INSTANCE.getRouter().navigateTo(Screens.DIALOGS_LIST)
    }
}