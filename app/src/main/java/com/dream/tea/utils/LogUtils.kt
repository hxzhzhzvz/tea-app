package com.dream.tea.utils

import android.util.Log

object LogUtils {

    var FLAG: Boolean = true

    fun e(tag: String, msg: String) {
        if (FLAG) {
            Log.e(tag, msg)
        }
    }
}