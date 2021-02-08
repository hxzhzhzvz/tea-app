package com.dream.tea.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dream.tea.R
import com.dream.tea.activity.LibraryVideoRecyclerActivity
import com.dream.tea.utils.ImageUtils
import com.dream.tea.vo.resp.VideoListRespVo

class LibraryVideoListAdapter :
    RecyclerView.Adapter<LibraryVideoListAdapter.ViewHolder>() {

    private val videoListListRespVoList = arrayListOf<VideoListRespVo>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoListImageView: ImageView = view.findViewById(R.id.libraryVideoListImage)
        val videoListTitleView: TextView = view.findViewById(R.id.libraryVideoListTitle)
        lateinit var videoListRespVo: VideoListRespVo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.library_video_list_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = videoListListRespVoList.size

    fun addItem(item: VideoListRespVo) {
        videoListListRespVoList.add(item)
        notifyItemInserted(itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoList = videoListListRespVoList[position]
        holder.videoListRespVo = videoList
        holder.videoListTitleView.text = videoList.name
        videoList.coverUrl?.let {
            ImageUtils.setImageUrl(
                holder.itemView.context,
                holder.videoListImageView,
                it
            )
        }

        holder.videoListImageView.setOnClickListener {
            holder.videoListRespVo.videoListId?.let { it1 ->
                LibraryVideoRecyclerActivity.actionStart(
                    holder.itemView.context,
                    it1
                )
            }
        }
    }

}