package com.cloudcoin.chat.raidachat.RAIDA

import com.cloudcoin.chat.raidachat.Extensions.debug
import com.cloudcoin.chat.raidachat.Model.Message
import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson

/**
 * Created by infraymer on 08.12.17.
 */
object RAIDA : ConnectionManager.WSListener {

    interface MessageListener {
        fun onGetMessage(message: Message)
    }

    interface AuthorizationListener {
        fun onAuthorization(success: Boolean)
    }

    private const val URL_1 = "ws://5.141.98.216:49011"
    private const val URL_2 = "ws://5.141.98.216:49012"

    /***********************************************************************************************
     * Properties
     **********************************************************************************************/
    private val mConnectionManager = ConnectionManager(this)
    private val mGson = Gson()

    var isConnect = false

    private val testUrlList = arrayListOf<String>()

    init {
        (11..20).mapTo(testUrlList) { "ws://5.141.98.216:490$it" }
        debug("Began connection")
        mConnectionManager.setWebSocketsList(testUrlList)
        mConnectionManager.connect()
    }

    /***********************************************************************************************
     * Private funs
     **********************************************************************************************/
    private fun responseAuthorization(response: RaidaResponse) {
        debug("responseAuthorization")
        debug(response.success.toString())
        debug(response.data.toString())

        
    }

    private fun responseRegistration(response: RaidaResponse) {

    }

    private fun responseCreateGroup(response: RaidaResponse) {

    }

    private fun responseAddMemberInGroup(response: RaidaResponse) {

    }

    private fun responseSendMessage(response: RaidaResponse) {

    }

    private fun responseGetMessage(response: RaidaResponse) {

    }

    private fun responseGetMyDialogs(response: RaidaResponse) {

    }

    /***********************************************************************************************
     * Public funs
     **********************************************************************************************/
    fun sendMessage(dialogID: String, message: Message) {
        val obj = jsonObject(
                "execFun" to "sendMsg",
                "data" to jsonObject(
                        "recipientId" to dialogID,
                        "toGroup" to message.mIsGroup,
                        "textMsg" to message.mText,
                        "guidMsg" to message.mId,
                        "sendTime" to message.mTimeSend,
                        "curFrg" to message.mCurrentFragment,
                        "totalFrg" to message.mTotalFragment
                )
        )
        val req = RaidaRequest(RaidaFunctions.SEND_MSG, message.toJson(dialogID))

    }

    fun getMessages() {
        val obj = jsonObject(
                "execFun" to "getMsg",
                "data" to jsonObject(
                        "getAll" to "true_or_false",
                        "onGroup" to "true_or_false",
                        "onlyId" to "sender_id - GUID"
                )
        )
    }

    fun getDialogs() {
        val obj = jsonObject(
                "execFun" to "getMyDialogs",
                "data" to jsonObject()
        )

    }

    fun authorization(login: String, password: String) {
        val obj = jsonObject(
                "execFun" to "authorization",
                "data" to jsonObject(
                        "login" to login,
                        "an" to password
                )
        )
        mConnectionManager.send(obj)
    }

    fun addUserToGroup(userId: String, groupId: String) {
        val obj = jsonObject(
                "execFun" to "addMemberInGroup",
                "data" to jsonObject(
                        "memberId" to userId,
                        "groupId" to groupId
                )
        )
    }

    fun createNewGroup(name: String, id: String) {
        val obj = jsonObject(
                "execFun" to "createGroup",
                "data" to jsonObject(
                        "groupName" to name,
                        "public_id" to id
                )
        )
    }

    fun registration(login: String, password: String) {
        val obj = jsonObject(
                "execFun" to "registration",
                "data" to jsonObject(
                        "login" to login,
                        "an" to password
                )
        )
        mConnectionManager.send(obj)
    }

    fun createConnect() {

    }

    /***********************************************************************************************
     * Extension
     **********************************************************************************************/

    /***********************************************************************************************
     * RAIDA WS LISTENER
     **********************************************************************************************/
    override fun onMessage(text: String) {
        val response = mGson.fromJson<RaidaResponse>(text)
        debug("onMessage: $text")
        if (response.callFunction != null)
            when (response.callFunction) {
                RaidaFunctions.REGISTRATION -> responseRegistration(response)
                RaidaFunctions.AUTHORIZATION -> responseAuthorization(response)
                RaidaFunctions.ADD_MEMBER_IN_GROUP -> responseAddMemberInGroup(response)
                RaidaFunctions.CREATE_GROUP -> responseCreateGroup(response)
                RaidaFunctions.GET_MSG -> responseGetMessage(response)
                RaidaFunctions.GET_MY_DIALOGS -> responseGetMyDialogs(response)
                RaidaFunctions.SEND_MSG -> responseSendMessage(response)
                else -> throw Exception("unknown call function!")
            }
    }
}