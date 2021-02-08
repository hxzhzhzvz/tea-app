package com.dream.tea.vo.resp

/**
 * 服务端返回的数据VO封装类
 */
class RespResult<T> {

    /**
     * 状态码
     */
    val code: Int? = null

    /**
     * 提示信息
     */
    val msg: String? = null

    /**
     * 返回的数据信息
     */
    val data: T? = null
}