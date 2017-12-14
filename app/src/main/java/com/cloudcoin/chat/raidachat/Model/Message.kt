package com.cloudcoin.chat.raidachat.Model

import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.stfalcon.chatkit.commons.models.IMessage
import java.util.*

/**
 * Created by infraymer on 05.12.17.
 */
class Message : IMessage {

    @SerializedName("guidMsg")
    lateinit var mId: String

    @SerializedName("textMsg")
    lateinit var mText: String

    @SerializedName("sender")
    lateinit var mSender: String

    @SerializedName("group")
    var mIsGroup = false

    @SerializedName("curFrg")
    var mCurrentFragment = 0

    @SerializedName("totalFrg")
    var mTotalFragment = 0

    @SerializedName("sendTime")
    var mTimeSend: Long = 0

    /***********************************************************************************************
     * Public funs
     **********************************************************************************************/
    fun toJson(dialogID: String): String {
        return jsonObject(
                "recipientId" to dialogID,
                "toGroup" to mIsGroup,
                "textMsg" to mText,
                "guidMsg" to mId,
                "sendTime" to mTimeSend,
                "curFrg" to mCurrentFragment,
                "totalFrg" to mTotalFragment
        ).string
    }

    /***********************************************************************************************
     * IMessage
     **********************************************************************************************/
    override fun getId(): String = mId

    override fun getCreatedAt(): Date = Date(mTimeSend)

    override fun getUser(): User {
        val user = User()
        user.mId = mSender
        user.mName = mSender.split("-").first()
        user.mAvatar = ""
        return user
    }

    override fun getText(): String = mText
}