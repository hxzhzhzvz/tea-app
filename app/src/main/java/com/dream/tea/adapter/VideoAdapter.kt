package com.dream.tea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.JzvdStd
import com.dream.tea.R
import com.dream.tea.activity.LibraryVideoPlayActivity
import com.dream.tea.utils.ImageUtils
import com.dream.tea.vo.resp.VideoRespVo

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val videoRespVoList = arrayListOf<VideoRespVo>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var videoRespVo: VideoRespVo
        var jzVideo: JzvdStd = view.findViewById(R.id.jzVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.library_video_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoRespVoList.size
    }

    fun addItem(item: VideoRespVo) {
        videoRespVoList.add(item)
        notifyItemInserted(itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoRespVoList[position]
        holder.videoRespVo = video
        val jzVideo: JzvdStd = holder.jzVideo
        video.coverUrl?.let {
            ImageUtils.setImageUrl(
                jzVideo.context,
                jzVideo.posterImageView,
                it
            )
        }
        jzVideo.setUp(
            video.videoUrl
            , video.name
        )
        jzVideo.fullscreenButton.setOnClickListener {
            video.videoUrl?.let { it ->
                LibraryVideoPlayActivity.actionStart(
                    holder.itemView.context, video.name ?: "", video.coverUrl ?: "", it
                )
            }
        }
    }
}