package com.cloudcoin.chat.raidachat.Mvp.Views

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.cloudcoin.chat.raidachat.Model.DefaultDialog

/**
 * Created by infraymer on 06.12.17.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface DialogsListView : MvpView {

    fun setDialogsList(dialogs: ArrayList<DefaultDialog>)
}