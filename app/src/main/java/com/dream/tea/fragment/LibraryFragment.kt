package com.dream.tea.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dream.tea.R
import com.dream.tea.activity.BannerActivity
import com.dream.tea.adapter.LibraryVideoListAdapter
import com.dream.tea.constants.App
import com.dream.tea.constants.RespCode
import com.dream.tea.service.BannerService
import com.dream.tea.service.LibraryVideoService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.vo.resp.RespResult
import com.dream.tea.vo.resp.VideoListRespVo
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.library_video_list_recycler.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class LibraryFragment : Fragment() {


    private lateinit var banner: Banner

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.navigation_library_fragment, container, false)
        banner = view.findViewById(R.id.banner)
        recyclerView = view.findViewById(R.id.libraryVideoListRecycler)
        recyclerView.isNestedScrollingEnabled = false
        return view
    }

    override fun onStart() {
        super.onStart()
        initBanner()
        initRecyclerView()
    }

    /**
     *  初始化网格布局数据
     */
    private fun initRecyclerView() {
        val adapter = LibraryVideoListAdapter()
        libraryVideoListRecycler.layoutManager =
            GridLayoutManager(recyclerView.context, 2)
        libraryVideoListRecycler.adapter = adapter
        thread {
            val libraryVideoService = ServiceCreator.create<LibraryVideoService>()
            libraryVideoService.getVideoListForLibrary(App.token)
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
                            val videoLists = resp?.data
                            if (videoLists != null && videoLists.isNotEmpty()) {
                                videoLists.forEach {
                                    adapter.addItem(it)
                                }
                            }
                        }
                    }
                })
        }
    }

    /**
     * 初始化banner数据
     */
    private fun initBanner() {
        thread {
            val bannerService = ServiceCreator.create<BannerService>()
            bannerService.getBannerList()
                .enqueue(object : Callback<RespResult<List<com.dream.tea.vo.resp.BannerRespVo>>> {
                    override fun onFailure(
                        call: Call<RespResult<List<com.dream.tea.vo.resp.BannerRespVo>>>,
                        t: Throwable
                    ) {
                        t.message?.let { Log.e(TAG, it) }
                    }

                    override fun onResponse(
                        call: Call<RespResult<List<com.dream.tea.vo.resp.BannerRespVo>>>,
                        response: Response<RespResult<List<com.dream.tea.vo.resp.BannerRespVo>>>
                    ) {
                        if (response.code() == RespCode.REQUEST_SUCCESS) {
                            val resp = response.body()
                            val bannerList = resp?.data
                            if (bannerList != null && bannerList.isNotEmpty()) {
                                val titleList = ArrayList<String>()
                                val coverImageList = ArrayList<String>()
                                val bannerMap = hashMapOf<Int, String>()
                                if (bannerList.isNotEmpty()) {
                                    for (index in bannerList.indices) {
                                        val bannerRespVoData: com.dream.tea.vo.resp.BannerRespVo =
                                            bannerList[index]
                                        if (!TextUtils.isEmpty(bannerRespVoData.coverUrl)) {
                                            titleList.add(bannerRespVoData.title ?: "")
                                            coverImageList.add(bannerRespVoData.coverUrl.toString())
                                            bannerMap[index] = bannerRespVoData.linkUrl ?: ""
                                        }
                                    }
                                }
                                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                                banner.setImageLoader(MyLoader())
                                banner.setBannerTitles(titleList)
                                banner.setImages(coverImageList)
                                banner.setBannerAnimation(Transformer.Default)
                                banner.setDelayTime(2000)
                                banner.isAutoPlay(true)
                                banner.setIndicatorGravity(BannerConfig.CENTER)
                                banner.start()
                                banner.setOnBannerListener {
                                    val linkUrl = bannerMap[it]
                                    if (!TextUtils.isEmpty(linkUrl)) {
                                        if (context != null && linkUrl != null) {
                                            BannerActivity.actionStart(context!!, linkUrl)
                                        }
                                    }
                                }
                            }
                        }
                    }
                })
        }
    }

    companion object {

        private const val TAG = "LibraryFragment"

    }
}

/**
 *  自定义图片加载类
 */
private class MyLoader : ImageLoader() {
    override fun displayImage(
        context: Context,
        path: Any,
        imageView: ImageView
    ) {
        Glide.with(context).load(path).into(imageView)
    }
}