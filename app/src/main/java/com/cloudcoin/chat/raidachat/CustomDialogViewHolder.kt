package com.cloudcoin.chat.raidachat

import android.view.View
import android.widget.TextView
import com.cloudcoin.chat.raidachat.Model.DefaultDialog
import com.stfalcon.chatkit.dialogs.DialogsListAdapter
import org.jetbrains.anko.find

/**
 * Created by infraymer on 06.12.17.
 */
class CustomDialogViewHolder(itemView: View) : DialogsListAdapter.DialogViewHolder<DefaultDialog>(itemView) {

    var avatarTextView = itemView.find<TextView>(R.id.dialogAvatarTv)


    override fun onBind(dialog: DefaultDialog) {
        super.onBind(dialog)
        avatarTextView.text = dialog.mDialogName[0].toString()
    }
}