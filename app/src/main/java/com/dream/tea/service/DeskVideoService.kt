package com.dream.tea.service

import com.dream.tea.vo.resp.RespResult
import com.dream.tea.vo.resp.VideoListRespVo
import com.dream.tea.vo.resp.VideoRespVo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DeskVideoService {

    @GET(ServiceCreator.TEA_CONTEXT_PATH + "/o/userVideo/getVideoListForDesk")
    fun getVideoListForDesk(): Call<RespResult<List<VideoListRespVo>>>

    @GET(ServiceCreator.TEA_CONTEXT_PATH + "/o/video/getByVideoListId/")
    fun getByVideoListId(@Query("videoListId") videoListId: Long): Call<RespResult<List<VideoRespVo>>>

}