package com.dream.tea.login

import android.content.Context
import android.text.TextUtils
import com.dream.tea.constants.App
import com.dream.tea.constants.RespCode
import com.dream.tea.service.LoginService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.utils.LogUtils
import com.dream.tea.vo.req.LoginReqVo
import com.dream.tea.vo.resp.LoginRespVo
import com.dream.tea.vo.resp.RespResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

object LoginUtils {

    private const val TAG = "LoginUtils"

    /**
     * 本地记录用户的账户密码
     */
    fun setAccountAndPassword(account: String, password: String, context: Context) {
        val editor =
            context.getSharedPreferences(App.LOGIN_DB, Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.putString(App.ACCOUNT, account)
        editor.putString(App.PASSWORD, password)
        editor.apply()
    }

    /**
     * 获取用户的账号
     */
    fun getAccount(context: Context): String {
        val prefs = context.getSharedPreferences(App.LOGIN_DB, Context.MODE_PRIVATE)
        return prefs.getString(App.ACCOUNT, App.EMPTY_STR) ?: ""
    }

    /**
     * 获取用户的密码
     */
    fun getPassword(context: Context): String {
        val prefs = context.getSharedPreferences(App.LOGIN_DB, Context.MODE_PRIVATE)
        return prefs.getString(App.PASSWORD, App.EMPTY_STR) ?: ""
    }

    /**
     * 自动尝试登陆操作
     */
    fun login(context: Context) {
        thread {
            val account = getAccount(context)
            val password = getPassword(context)
            if ((!TextUtils.isEmpty(account)) && (!TextUtils.isEmpty(password))) {
                val loginReqVo = LoginReqVo()
                loginReqVo.account = account
                loginReqVo.password = password
                val loginService = ServiceCreator.create<LoginService>()
                loginService.login(loginReqVo)
                    .enqueue(object : Callback<RespResult<LoginRespVo>> {
                        override fun onFailure(call: Call<RespResult<LoginRespVo>>, t: Throwable) {
                            t.message?.let { LogUtils.e(TAG, it) }
                        }

                        override fun onResponse(
                            call: Call<RespResult<LoginRespVo>>,
                            response: Response<RespResult<LoginRespVo>>
                        ) {
                            if (response.code() == RespCode.REQUEST_SUCCESS) {
                                val resp = response.body()
                                if ((resp != null) && (resp.code == RespCode.SUCCESS_CODE)) {
                                    val loginRespVo = resp.data
                                    if (loginRespVo != null) {
                                        App.token = loginRespVo.token
                                    }
                                }
                            }
                        }
                    })
            }
        }
    }

}