package com.dream.tea.service

import com.dream.tea.vo.req.FeedbackReqVo
import com.dream.tea.vo.resp.DeveloperRespVo
import com.dream.tea.vo.resp.RespResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppSystemService {

    @POST(ServiceCreator.TEA_CONTEXT_PATH + "/o/app/feedback")
    fun feedback(@Body reqVo: FeedbackReqVo): Call<RespResult<Void>>

    @GET(ServiceCreator.TEA_CONTEXT_PATH + "/o/app/getDeveloperNotes")
    fun getDeveloperNotes(): Call<RespResult<DeveloperRespVo>>
}