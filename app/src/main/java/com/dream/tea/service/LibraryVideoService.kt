package com.dream.tea.service

import com.dream.tea.constants.App
import com.dream.tea.vo.req.AddVideoListReqVo
import com.dream.tea.vo.resp.RespResult
import com.dream.tea.vo.resp.VideoListRespVo
import com.dream.tea.vo.resp.VideoRespVo
import retrofit2.Call
import retrofit2.http.*

interface LibraryVideoService {

    @GET(ServiceCreator.TEA_CONTEXT_PATH + "/o/video/getVideoListForLibrary")
    fun getVideoListForLibrary(@Header(App.tokenName) token: String): Call<RespResult<List<VideoListRespVo>>>

    @GET(ServiceCreator.TEA_CONTEXT_PATH + "/o/video/getByVideoListId/")
    fun getByVideoListId(@Query("videoListId") videoListId: Long): Call<RespResult<List<VideoRespVo>>>

    @POST(ServiceCreator.TEA_CONTEXT_PATH + "/o/userVideo/addVideoList")
    fun addVideoList(@Body reqVoVo: AddVideoListReqVo): Call<RespResult<Void>>
}