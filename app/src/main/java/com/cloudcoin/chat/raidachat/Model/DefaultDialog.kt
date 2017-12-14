package com.cloudcoin.chat.raidachat.Model

import com.stfalcon.chatkit.commons.models.IDialog

/**
 * Created by infraymer on 05.12.17.
 */
class DefaultDialog : IDialog<Message> {

    lateinit var mId: String
    lateinit var mLastMessage: Message
    lateinit var mDialogName: String
    lateinit var mUsers: MutableList<User>
    lateinit var mDialogPhoto: String
    var mUnreadCount: Int = 0


    /***********************************************************************************************
     * IDialog
     ***********************************************************************************************/
    override fun getLastMessage(): Message = mLastMessage

    override fun getDialogName(): String = mDialogName

    override fun getUsers(): MutableList<User> = mUsers

    override fun setLastMessage(message: Message?) {
        mLastMessage = message!!
    }

    override fun getUnreadCount(): Int = mUnreadCount

    override fun getId(): String = mId

    override fun getDialogPhoto(): String = mDialogPhoto


}