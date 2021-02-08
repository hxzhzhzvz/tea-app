package com.dream.tea.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import cn.jzvd.JzvdStd
import com.dream.tea.R
import com.dream.tea.adapter.VideoAdapter
import com.dream.tea.constants.RespCode
import com.dream.tea.utils.LogUtils
import com.dream.tea.service.LibraryVideoService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.vo.req.AddVideoListReqVo
import com.dream.tea.vo.resp.RespResult
import com.dream.tea.vo.resp.VideoRespVo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.libray_video_recycler.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class LibraryVideoRecyclerActivity : AppCompatActivity() {

    private var videoListId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.libray_video_recycler)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        videoListId = intent.getLongExtra("videoListId", 0)
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        initAddButtonView()
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(this, 1)
        libraryCourseRecycler.layoutManager = layoutManager
        val adapter = VideoAdapter()
        libraryCourseRecycler.adapter = adapter
        if (videoListId > 0) {
            thread {
                val libraryVideoService = ServiceCreator.create<LibraryVideoService>()
                libraryVideoService.getByVideoListId(videoListId)
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
                                if (resp?.code == RespCode.SUCCESS_CODE) {
                                    val videoList = resp.data
                                    if (videoList != null && videoList.isNotEmpty()) {
                                        videoList.forEach { adapter.addItem(it) }
                                    }
                                }
                            }
                        }
                    })
            }
        }

    }

    private fun initAddButtonView() {
        val context: Context = this
        val addVideoListToDesk: FloatingActionButton = addVideoListToDesk

        addVideoListToDesk.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("提示信息")
                setMessage("确定要收藏该视频列表吗？")
                setCancelable(false)
                setPositiveButton("确定") { _, _ ->
                    val reqVo = AddVideoListReqVo()
                    reqVo.videoListId = videoListId
                    val libraryVideoService = ServiceCreator.create<LibraryVideoService>()
                    libraryVideoService.addVideoList(reqVo)
                        .enqueue(object : Callback<RespResult<Void>> {
                            override fun onFailure(call: Call<RespResult<Void>>, t: Throwable) {
                                t.message?.let { it1 -> LogUtils.e(TAG, it1) }
                            }

                            override fun onResponse(
                                call: Call<RespResult<Void>>,
                                response: Response<RespResult<Void>>
                            ) {
                                if (response.code() == RespCode.REQUEST_SUCCESS) {
                                    val resp = response.body()
                                    if (resp != null) {
                                        val code = resp.code ?: -1
                                        if (code == RespCode.NOT_LOGIN_YET) {
                                            LoginActivity.actionStart(context)
                                        }
                                        if (code != RespCode.SUCCESS_CODE) {
                                            Toast.makeText(
                                                context,
                                                "收藏失败，" + resp.msg,
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                        } else {
                                            Toast.makeText(context, "收藏成功", Toast.LENGTH_LONG)
                                                .show()
                                        }
                                    }
                                }
                            }
                        })

                }
                setNegativeButton("取消") { _, _ ->
                    // do noting
                }
            }.show()
        }
    }

    /**
     * 返回操作
     */
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
        private const val TAG = "LibraryVideoRecyclerActivity"

        fun actionStart(context: Context, videoListId: Long) {
            val intent = Intent(context, LibraryVideoRecyclerActivity::class.java)
            intent.putExtra("videoListId", videoListId)
            context.startActivity(intent)
        }
    }
}