package com.dream.tea.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dream.tea.R
import com.dream.tea.activity.LoginActivity
import com.dream.tea.adapter.DeskVideoListAdapter
import com.dream.tea.constants.App
import com.dream.tea.constants.RespCode
import com.dream.tea.service.DeskVideoService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.vo.resp.RespResult
import com.dream.tea.vo.resp.VideoListRespVo
import kotlinx.android.synthetic.main.desk_video_list_recycler.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class DeskFragment : Fragment() {

    lateinit var deskDefaultPageText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.navigation_desk_fragment, container, false)
        deskDefaultPageText = view.findViewById(R.id.desk_default_page_text)
        return view
    }

    override fun onStart() {
        super.onStart()
        initVideoListRecycler()
    }

    private fun initVideoListRecycler() {
        val context = this.context
        if (context != null && TextUtils.isEmpty(App.token)) {
            LoginActivity.actionStart(context)
            return
        }
        val videoListAdapter = DeskVideoListAdapter()
        desk_video_list_recycler.layoutManager =
            GridLayoutManager(context, 1)
        desk_video_list_recycler.adapter = videoListAdapter
        thread {
            val deskVideoService = ServiceCreator.create<DeskVideoService>()
            deskVideoService.getVideoListForDesk()
                .enqueue(object : Callback<RespResult<List<VideoListRespVo>>> {
                    override fun onFailure(
                        call: Call<RespResult<List<VideoListRespVo>>>,
                        t: Throwable
                    ) {
                        t.message?.let { Log.e(TAG, it) }
                    }

                    override fun onResponse(
                        call: Call<RespResult<List<VideoListRespVo>>>,
                        response: Response<RespResult<List<VideoListRespVo>>>
                    ) {
                        if (response.code() == RespCode.REQUEST_SUCCESS) {
                            val resp = response.body()
                            if (resp != null) {
                                if (resp.code == RespCode.NOT_LOGIN_YET) {
                                    if (context != null) {
                                        LoginActivity.actionStart(context)
                                    }
                                }
                                if (resp.code != RespCode.SUCCESS_CODE) {
                                    Toast.makeText(
                                        context,
                                        "获取收藏列表失败，" + resp.msg,
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                }
                                val videoListRespVoList = resp.data
                                if ((videoListRespVoList == null) || videoListRespVoList.isEmpty()) {
                                    deskDefaultPageText.setText(R.string.desk_default_page_text)
                                } else {
                                    videoListRespVoList.forEach {
                                        videoListAdapter.addItem(it)
                                    }
                                }
                            }
                        }
                    }
                })
        }
    }

    companion object {

        private const val TAG = "DeskFragment"
    }
}