package com.cloudcoin.chat.raidachat.RAIDA

import android.util.Base64
import com.cloudcoin.chat.raidachat.Extensions.debug
import com.cloudcoin.chat.raidachat.Extensions.err
import com.cloudcoin.chat.raidachat.Model.DefaultDialog
import com.cloudcoin.chat.raidachat.Model.Message
import com.github.salomonbrys.kotson.fromJson
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.Gson
import java.nio.charset.Charset

/**
 * Created by infraymer on 08.12.17.
 */
object RAIDA : ConnectionManager.WSListener {

    interface MessageListener {
        fun onGetMessage()
    }

    interface OnLoadListener {
        fun onLoaded(dialogs: ArrayList<DefaultDialog>, messages: ArrayList<Message>)
    }

    interface AuthorizationListener {
        fun onAuthorization(success: Boolean, errorMessage: String?)
    }

    interface DialogsListener {
        fun onGetDialogs(success: Boolean, dialogs: ArrayList<DefaultDialog>, errorMessage: String?)
    }

    private const val URL_1 = "ws://5.141.98.216:49011"
    private const val URL_2 = "ws://5.141.98.216:49012"

    /***********************************************************************************************
     * Properties
     **********************************************************************************************/
    private val mConnectionManager = ConnectionManager(this)
    private val mGson = Gson()
    private val testUrlList = arrayListOf<String>()

    private var mAuthListener: AuthorizationListener? = null
    private var mDialogListener: DialogsListener? = null
    private var mMessageListener: MessageListener? = null
    private var mOnLoadListener: OnLoadListener? = null

    private var mAuthorizationCounter = 0
    private var mGetMyDialogsCounter = 0
    private var mGetMessageCounter = 0

    private var mPartsOfMessages = arrayListOf<Message>()
    private var mMessages = arrayListOf<Message>()
    private var mDialogs = arrayListOf<DefaultDialog>()

    private var mDialogIsLoaded = false
        set(value) {
            field = value
            if (value && mMessagesIsLoaded) mOnLoadListener?.onLoaded(mDialogs, mMessages)
        }
    private var mMessagesIsLoaded = false
        set(value) {
            field = value
            if (value && mDialogIsLoaded) mOnLoadListener?.onLoaded(mDialogs, mMessages)
        }

    var isConnect = false


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
        mAuthorizationCounter++
        if (mAuthorizationCounter == 10) {
            debug("responseAuthorization")
            mAuthorizationCounter = 0
            mAuthListener?.onAuthorization(response.success!!, response.msgError)
        }
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
        val messages = mGson.fromJson<ArrayList<Message>>(mGson.toJson(response.data))
        mPartsOfMessages.addAll(messages)
        mGetMessageCounter++
        if (mGetMessageCounter == 10) {
            try {
                debug("responseGetMessage")
                mGetMessageCounter = 0

                // сортируем массив с кусочками сообщений
                val list = mPartsOfMessages.sortedWith(compareBy({ it.mId }, { it.mCurrentFragment }))
                // Вывод в лог
//            list.forEach { debug("${it.mId} / ${it.mCurrentFragment} / ${it.mText}") }
                // Собираем кусочки
                val messages = arrayListOf<Message>()
                var currentMessage = 0
                var i = 0
                while (currentMessage != list.size) {
                    var text = ""
                    var j = 0
                    while (j < list[currentMessage].mText.length) {
                        var k = 0
                        while (k < list[currentMessage].mTotalFragment) {
                            if (list[currentMessage + k].mText.length <= list[currentMessage].mText.length)
                                text += list[currentMessage + k].mText[j]
                            k++
                        }
                        j++
                    }
                    list[currentMessage].mText = text
                    messages.add(list[currentMessage])
                    currentMessage += list[currentMessage].mTotalFragment
                }
                // Вывод в лог
                messages.forEach { debug("${it.mId} / ${it.mCurrentFragment} / ${it.mText}") }
                // Декодируем
                messages.forEach {
                    try {
                        it.mText = Base64.decode(it.mText, Base64.DEFAULT).toString(Charset.forName("UTF-8"))
                        debug(it.mText)
                    } catch (e: Exception) {
                        err(e.message.toString())
                    }
                }
                // Дичь
                mMessageListener?.onGetMessage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun responseGetMyDialogs(response: RaidaResponse) {
        mGetMyDialogsCounter++
        if (mGetMyDialogsCounter == 10) {
            debug("responseGetMyDialogs")
            mGetMyDialogsCounter = 0
            val dialogs = arrayListOf<DefaultDialog>()
            val jsonDialogs = mGson.toJsonTree(response.data).asJsonArray
            jsonDialogs.forEach {
                val d = DefaultDialog()
                d.mName = it["name"].asString
                d.mId = it["id"].asString
                d.mIsGroup = it["group"].asBoolean
                dialogs.add(d)
            }
            mDialogListener?.onGetDialogs(response.success!!, dialogs, response.msgError)
        }
    }

    /***********************************************************************************************
     * Listeners
     **********************************************************************************************/
    fun setAuthListener(l: (success: Boolean, errorMessage: String?) -> Unit) {
        mAuthListener = object : AuthorizationListener {
            override fun onAuthorization(success: Boolean, errorMessage: String?) {
                l(success, errorMessage)
            }
        }
    }

    fun setDialogsListener(l: (success: Boolean, dialogs: ArrayList<DefaultDialog>, errorMessage: String?) -> Unit) {
        mDialogListener = object : DialogsListener {
            override fun onGetDialogs(success: Boolean, dialogs: ArrayList<DefaultDialog>, errorMessage: String?) {
                l(success, dialogs, errorMessage)
            }
        }
    }

    fun setMessageListener(l: () -> Unit) {
        mMessageListener = object : MessageListener {
            override fun onGetMessage() {
                l()
            }
        }
    }

    fun setOnLoadListener(l: (dialogs: ArrayList<DefaultDialog>, messages: ArrayList<Message>) -> Unit) {
        mOnLoadListener = object : OnLoadListener {
            override fun onLoaded(dialogs: ArrayList<DefaultDialog>, messages: ArrayList<Message>) {
                l(dialogs, messages)
            }
        }
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
                        "getAll" to "true"
//                        "onGroup" to "true_or_false",
//                        "onlyId" to "sender_id - GUID"
                )
        )
        mConnectionManager.send(obj)
    }

    fun getDialogs() {
        val obj = jsonObject(
                "execFun" to "getMyDialogs",
                "data" to jsonObject()
        )
        mConnectionManager.send(obj)
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