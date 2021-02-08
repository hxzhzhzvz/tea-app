package com.dream.tea.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.JzvdStd
import com.dream.tea.R
import com.dream.tea.utils.ImageUtils

class LibraryVideoPlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.library_video_card_view)
        supportActionBar?.hide()
        val jzVideo: JzvdStd = findViewById(R.id.jzVideo)
        val name = intent.getStringExtra("name")
        val coverUrl = intent.getStringExtra("coverUrl")
        val videoUrl = intent.getStringExtra("videoUrl")
        if (coverUrl != null) {
            ImageUtils.setImageUrl(
                jzVideo.context,
                jzVideo.posterImageView,
                coverUrl
            )
        }
        jzVideo.setUp(
            videoUrl
            , name
        )
        jzVideo.gotoFullscreen()
        jzVideo.startPreloading()
        jzVideo.startVideoAfterPreloading()
        jzVideo.backButton.setOnClickListener {
            jzVideo.state = JzvdStd.STATE_PAUSE
            this.finish()
        }

        jzVideo.fullscreenButton.setOnClickListener {
            jzVideo.state = JzvdStd.STATE_PAUSE
            this.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JzvdStd.releaseAllVideos()
    }

    companion object {
        fun actionStart(context: Context, name: String, coverUrl: String, videoUrl: String) {
            val intent = Intent(context, LibraryVideoPlayActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("coverUrl", coverUrl)
            intent.putExtra("videoUrl", videoUrl)
            context.startActivity(intent)
        }
    }
}