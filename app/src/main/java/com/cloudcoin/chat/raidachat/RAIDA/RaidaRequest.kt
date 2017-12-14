package com.cloudcoin.chat.raidachat.RAIDA

import com.google.gson.annotations.SerializedName

/**
 * Created by infraymer on 14.12.2017.
 */
class RaidaRequest(execFun: String, data: String) {

    @SerializedName("execFun")
    var execFun: String? = execFun

    @SerializedName("data")
    var data: String? = data
}