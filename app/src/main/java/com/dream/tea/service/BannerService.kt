package com.dream.tea.service

import com.dream.tea.vo.resp.BannerRespVo
import com.dream.tea.vo.resp.RespResult
import retrofit2.Call
import retrofit2.http.GET

interface BannerService {

    /**
     * 获取banner列表
     */
    @GET(ServiceCreator.TEA_CONTEXT_PATH + "/o/banner/getBannerList")
    fun getBannerList(): Call<RespResult<List<BannerRespVo>>>
}