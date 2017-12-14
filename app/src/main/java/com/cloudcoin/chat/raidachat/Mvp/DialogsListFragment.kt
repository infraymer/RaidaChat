package com.cloudcoin.chat.raidachat.Mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.cloudcoin.chat.raidachat.CustomDialogViewHolder
import com.cloudcoin.chat.raidachat.Model.DefaultDialog
import com.cloudcoin.chat.raidachat.Mvp.Presenters.DialogsListPresenter
import com.cloudcoin.chat.raidachat.Mvp.Views.DialogsListView
import com.cloudcoin.chat.raidachat.R
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import kotlinx.android.synthetic.main.fragment_dialogs_list.*
import org.jetbrains.anko.appcompat.v7.titleResource
import kotlin.collections.ArrayList

/**
 * Created by infraymer on 29.11.17.
 */
class DialogsListFragment : MvpAppCompatFragment(), DialogsListView {

    companion object {
        fun newInstance(data: Any?): DialogsListFragment {
            val fragment = DialogsListFragment()
            return fragment
        }
    }

    /***********************************************************************************************
    * Properties
    ***********************************************************************************************/
    @InjectPresenter
    lateinit var mPresenter: DialogsListPresenter

    private lateinit var mDialogsListAdapter: DialogsListAdapter<DefaultDialog>

    /***********************************************************************************************
    * Override funs
    ***********************************************************************************************/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_dialogs_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogs_list_toolbar.titleResource = R.string.dialogs_list_title

        mDialogsListAdapter = DialogsListAdapter(R.layout.item_dialog_custom, CustomDialogViewHolder::class.java, ImageLoader { imageView, url -> })
        mDialogsListAdapter
        dialogs_list.setAdapter(mDialogsListAdapter, false)

        /*for (i in 0..1) {
            val author1 = User()
            author1.mAvatar = ""
            author1.mId = UUID.randomUUID().toString()
            author1.mName = "First"

            val author2 = User()
            author1.mAvatar = ""
            author1.mId = UUID.randomUUID().toString()
            author1.mName = "Second"

            val message = Message()
            message.mId = UUID.randomUUID().toString()
            message.mUser = author1
            message.mText = "Hello, world"
            message.mCreatedAt = Date()

            val dialog = DefaultDialog()
            dialog.mId = UUID.randomUUID().toString()
            dialog.mLastMessage = message
            dialog.mUsers = mutableListOf(author1, author2)
            dialog.mDialogName = "Dialog $i"
            dialog.mDialogPhoto = ""
            dialog.mUnreadCount = 1

            mDialogsListAdapter.addItem(dialog)
        }*/
    }

    /***********************************************************************************************
     * DialogsListView
     ***********************************************************************************************/
    override fun setDialogsList(dialogs: ArrayList<DefaultDialog>) {
        mDialogsListAdapter.setItems(dialogs)
    }
}