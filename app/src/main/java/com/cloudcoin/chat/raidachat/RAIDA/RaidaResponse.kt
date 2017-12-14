package com.cloudcoin.chat.raidachat.RAIDA

import com.google.gson.annotations.SerializedName

/**
 * Created by infraymer on 14.12.2017.
 */
class RaidaResponse {

    @SerializedName("callFunction")
    val callFunction: String? = null

    @SerializedName("success")
    val success: Boolean? = null

    @SerializedName("msgError")
    val msgError: String? = null

    @SerializedName("data")
    val data: Any? = null
}