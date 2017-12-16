package com.cloudcoin.chat.raidachat.Mvp.Presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.cloudcoin.chat.raidachat.Extensions.debug
import com.cloudcoin.chat.raidachat.Mvp.Views.DialogsListView
import com.cloudcoin.chat.raidachat.RAIDA.RAIDA

/**
 * Created by infraymer on 05.12.17.
 */
@InjectViewState
class DialogsListPresenter : MvpPresenter<DialogsListView>() {

    init {
        debug("init")
        /*RAIDA.setDialogsListener { success, dialogs, errorMessage ->
            if (success) {
                viewState.setDialogsList(dialogs)
            } else {
                App.INSTANCE.getRouter().showSystemMessage(errorMessage)
            }
        }*/
        RAIDA.setOnLoadListener { dialogs, messages ->
            val msgSorted = messages.sortedWith(compareBy({it.mTimeSend}))
            dialogs.forEach { dialog ->
                if (dialog.mIsGroup) {
                    dialog.mLastMessage = msgSorted.last { it.mGroupName == dialog.mName }
                } else {
                    dialog.mLastMessage = msgSorted.last { it.mSender == dialog.mName }
                }
            }
            viewState.setDialogsList(dialogs)
        }
    }

    fun onViewCreated() {
        debug("onViewCreated")
        RAIDA.getDialogs()
        RAIDA.getMessages()
    }
}