package com.cloudcoin.chat.raidachat.Model

import com.stfalcon.chatkit.commons.models.IDialog

/**
 * Created by infraymer on 05.12.17.
 */
class DefaultDialog : IDialog<Message> {

    var mId: String? = null
    var mLastMessage: Message? = null
    var mName: String? = null
    var mAuthors: MutableList<Author> = mutableListOf()
//    lateinit var mDialogPhoto: String
    var mUnreadCount: Int = 0
    var mIsGroup = false

    /***********************************************************************************************
     * IDialog
     ***********************************************************************************************/
    override fun getLastMessage(): Message? = mLastMessage

    override fun getDialogName(): String = mName ?: ""

    override fun getUsers(): MutableList<Author> = mAuthors

    override fun setLastMessage(message: Message?) {
        mLastMessage = message
    }

    override fun getUnreadCount(): Int = mUnreadCount

    override fun getId(): String = mId?: ""

    override fun getDialogPhoto(): String = ""


}