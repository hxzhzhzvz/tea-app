package com.dream.tea.utils

import android.content.Context
import java.util.*

object PropUtils {

    fun getProps(context: Context): Properties {
        val props = Properties();
        props.load(context.assets.open("config.properties"))
        return props
    }
}