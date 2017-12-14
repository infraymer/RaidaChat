package com.cloudcoin.chat.raidachat.Model

import com.stfalcon.chatkit.commons.models.IUser

/**
 * Created by infraymer on 05.12.17.
 */
class User : IUser {

    lateinit var mId: String
    lateinit var mName: String
    lateinit var mAvatar: String


    /***********************************************************************************************
    * IUser
    ***********************************************************************************************/
    override fun getAvatar(): String = mAvatar

    override fun getName(): String = mName

    override fun getId(): String = mId
}