package com.dream.tea.constants

object App {
    /**
     * 域名
     */
    const val DOMAIN: String = "http://192.168.15.1:8050/tea"

    /**
     * 访问的token
     */
    var token: String = ""

    /**
     * 访问token的hear名字
     */
    const val tokenName: String = "user-auth"

    /**
     * 账号存储的地方
     */
    const val LOGIN_DB: String = "data"

    /**
     * 账号
     */
    const val ACCOUNT: String = "account"

    /**
     * 密码
     */
    const val PASSWORD: String = "password"

    /**
     * 空字符串
     */
    const val EMPTY_STR = ""
}