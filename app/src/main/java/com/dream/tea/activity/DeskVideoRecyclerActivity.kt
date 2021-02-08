package com.dream.tea.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import cn.jzvd.JzvdStd
import com.dream.tea.R
import com.dream.tea.adapter.VideoAdapter
import com.dream.tea.constants.RespCode
import com.dream.tea.utils.LogUtils
import com.dream.tea.service.DeskVideoService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.vo.resp.RespResult
import com.dream.tea.vo.resp.VideoRespVo
import kotlinx.android.synthetic.main.desk_video_recycler.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class DeskVideoRecyclerActivity : AppCompatActivity() {

    private var videoListId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.desk_video_recycler)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        videoListId = intent.getLongExtra("videoListId", 0)
    }

    override fun onStart() {
        initRecyclerView()
        super.onStart()
    }

    private fun initRecyclerView() {
        val context = this
        val layoutManager = GridLayoutManager(this, 1)
        desk_video_recycler.layoutManager = layoutManager
        val videoAdapter = VideoAdapter()
        desk_video_recycler.adapter = videoAdapter
        thread {
            val deskVideoService = ServiceCreator.create<DeskVideoService>()
            deskVideoService.getByVideoListId(videoListId)
                .enqueue(object : Callback<RespResult<List<VideoRespVo>>> {
                    override fun onFailure(
                        call: Call<RespResult<List<VideoRespVo>>>,
                        t: Throwable
                    ) {
                        t.message?.let { LogUtils.e(TAG, it) }
                    }

                    override fun onResponse(
                        call: Call<RespResult<List<VideoRespVo>>>,
                        response: Response<RespResult<List<VideoRespVo>>>
                    ) {
                        if (response.code() == RespCode.REQUEST_SUCCESS) {
                            val resp = response.body()
                            if (resp != null) {
                                if (resp.code == RespCode.SUCCESS_CODE) {
                                    val videoRespVoList = resp.data
                                    if (videoRespVoList != null && videoRespVoList.isNotEmpty()) {
                                        videoRespVoList.forEach { videoAdapter.addItem(it) }
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "获取视频列表失败，" + resp.msg,
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                            }
                        }
                    }
                })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        JzvdStd.releaseAllVideos()
    }

    companion object {

        private const val TAG = "DeskVideoRecyclerActivity"

        fun actionStart(context: Context, videoListId: Long) {
            val intent = Intent(context, DeskVideoRecyclerActivity::class.java)
            intent.putExtra("videoListId", videoListId)
            context.startActivity(intent)
        }
    }
}