package com.dream.tea.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.dream.tea.R
import com.dream.tea.constants.App
import com.dream.tea.constants.RespCode
import com.dream.tea.login.LoginUtils
import com.dream.tea.service.LoginService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.utils.LogUtils
import com.dream.tea.vo.req.LoginReqVo
import com.dream.tea.vo.req.RegisterReqVo
import com.dream.tea.vo.resp.LoginRespVo
import com.dream.tea.vo.resp.RegisterRespVo
import com.dream.tea.vo.resp.RespResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val context = this
        val login: Button = findViewById(R.id.login)
        val register: Button = findViewById(R.id.register)
        val accountText: EditText = findViewById(R.id.account)
        val passwordText: EditText = findViewById(R.id.password)
        accountText.setText(LoginUtils.getAccount(context))
        passwordText.setText(LoginUtils.getPassword(context))
        login.setOnClickListener {
            val account = accountText.text.toString()
            val password = passwordText.text.toString()
            if (!account.matches(Regex("^[a-zA-Z0-9]{8,18}$"))) {
                Toast.makeText(this, "账号必须是8到18位的数字或者字母", LENGTH_SHORT).show()
            }
            if (!password.matches(Regex("^[a-zA-Z0-9]{8,18}$"))) {
                Toast.makeText(this, "密码必须是8到18位的数字或者字母", LENGTH_SHORT).show()
            }
            if (account.matches(Regex("^[a-zA-Z0-9]{8,18}$")) && password.matches(Regex("^[a-zA-Z0-9]{8,18}$"))) {
                val loginService = ServiceCreator.create<LoginService>()
                val reqVo = LoginReqVo()
                reqVo.account = account
                reqVo.password = password
                loginService.login(reqVo)
                    .enqueue(object : Callback<RespResult<LoginRespVo>> {
                        override fun onFailure(call: Call<RespResult<LoginRespVo>>, t: Throwable) {
                            t.message?.let { it1 -> LogUtils.e(TAG, it1) }
                        }

                        override fun onResponse(
                            call: Call<RespResult<LoginRespVo>>,
                            response: Response<RespResult<LoginRespVo>>
                        ) {
                            if (response.code() == RespCode.REQUEST_SUCCESS) {
                                val resp = response.body()
                                if (resp != null) {
                                    if (resp.code == RespCode.SUCCESS_CODE) {
                                        LoginUtils.setAccountAndPassword(account, password, context)
                                        val loginRespVo = resp.data
                                        if (loginRespVo != null) {
                                            App.token = loginRespVo.token
                                            context.finish()
                                        }
                                    } else {
                                        Toast.makeText(context, "登录失败，" + resp.msg, LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }

                        }
                    })
            }
        }
        register.setOnClickListener {
            val account = accountText.text.toString()
            val password = passwordText.text.toString()
            if (!account.matches(Regex("^[a-zA-Z0-9]{8,18}$"))) {
                Toast.makeText(this, "账号必须是8到18位的数字或者字母", LENGTH_SHORT).show()
            }
            if (!password.matches(Regex("^[a-zA-Z0-9]{8,18}$"))) {
                Toast.makeText(this, "密码必须是8到18位的数字或者字母", LENGTH_SHORT).show()
            }
            if (account.matches(Regex("^[a-zA-Z0-9]{8,18}$")) && password.matches(Regex("^[a-zA-Z0-9]{8,18}$"))) {
                val loginService = ServiceCreator.create<LoginService>()
                val reqVo = RegisterReqVo()
                reqVo.account = account
                reqVo.password = password
                loginService.register(reqVo)
                    .enqueue(object : Callback<RespResult<RegisterRespVo>> {
                        override fun onFailure(
                            call: Call<RespResult<RegisterRespVo>>,
                            t: Throwable
                        ) {
                            t.message?.let { it1 -> LogUtils.e(TAG, it1) }
                        }

                        override fun onResponse(
                            call: Call<RespResult<RegisterRespVo>>,
                            response: Response<RespResult<RegisterRespVo>>
                        ) {
                            if (response.code() == RespCode.REQUEST_SUCCESS) {
                                val resp = response.body()
                                if (resp != null) {
                                    if (resp.code == RespCode.SUCCESS_CODE) {
                                        LoginUtils.setAccountAndPassword(account, password, context)
                                        val registerRespVo = resp.data
                                        if (registerRespVo != null) {
                                            App.token = registerRespVo.token
                                            context.finish()
                                        }
                                    } else {
                                        Toast.makeText(context, "注册失败，" + resp.msg, LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                        }
                    })
            }
        }
    }

    companion object {

        private const val TAG = "LoginActivity"

        fun actionStart(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
