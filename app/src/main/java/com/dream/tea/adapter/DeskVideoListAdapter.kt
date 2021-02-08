package com.dream.tea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dream.tea.R
import com.dream.tea.activity.DeskVideoRecyclerActivity
import com.dream.tea.utils.ImageUtils
import com.dream.tea.vo.resp.VideoListRespVo

class DeskVideoListAdapter :
    RecyclerView.Adapter<DeskVideoListAdapter.ViewHolder>() {

    private val videoListRespVoList = arrayListOf<VideoListRespVo>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deskVideoListImage: ImageView = view.findViewById(R.id.desk_video_list_image)
        val deskVideoListTitle: TextView = view.findViewById(R.id.desk_video_list_title)
        lateinit var videoListRespVo: VideoListRespVo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.desk_video_list_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = videoListRespVoList.size

    fun addItem(item: VideoListRespVo) {
        videoListRespVoList.add(item)
        notifyItemInserted(itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoListRespVo = videoListRespVoList[position]
        videoListRespVo.coverUrl?.let {
            ImageUtils.setImageUrl(
                holder.itemView.context,
                holder.deskVideoListImage,
                it
            )
        }
        holder.deskVideoListTitle.text = videoListRespVo.name
        holder.videoListRespVo = videoListRespVo
        holder.deskVideoListImage.setOnClickListener {
            videoListRespVo.videoListId?.let { it1 ->
                DeskVideoRecyclerActivity.actionStart(
                    holder.itemView.context,
                    it1
                )
            }
        }
    }
}