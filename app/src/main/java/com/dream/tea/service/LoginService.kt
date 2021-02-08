package com.dream.tea.service

import com.dream.tea.vo.req.LoginReqVo
import com.dream.tea.vo.req.RegisterReqVo
import com.dream.tea.vo.resp.LoginRespVo
import com.dream.tea.vo.resp.RegisterRespVo
import com.dream.tea.vo.resp.RespResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST(ServiceCreator.TEA_CONTEXT_PATH + "/o/user/register")
    fun register(@Body reqVo: RegisterReqVo): Call<RespResult<RegisterRespVo>>

    @POST(ServiceCreator.TEA_CONTEXT_PATH + "/o/user/login")
    fun login(@Body reqVo: LoginReqVo): Call<RespResult<LoginRespVo>>
}