@file:Suppress("NOTHING_TO_INLINE")

package com.cloudcoin.chat.raidachat.Extensions

import android.util.Log

/**
 * Created by infraymer on 20.11.17.
 */

inline fun Any.debug(message: String) {
    Log.d(javaClass.simpleName, message)
}

inline fun Any.err(message: String) {
    Log.e(javaClass.simpleName, message)
}