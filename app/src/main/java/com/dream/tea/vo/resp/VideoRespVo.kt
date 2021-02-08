package com.dream.tea.vo.resp

class VideoRespVo {

    /**
     * 视频id
     */
    var videoId: Long? = null

    /**
     * 该视频所属的视频资源列表的id
     */
    var videoListId: Long? = null

    /**
     * 视频的名字
     */
    var name: String? = null

    /**
     * 视频的封面
     */
    var coverUrl: String? = null

    /**
     * 视频的链接
     */
    var videoUrl: String? = null
}