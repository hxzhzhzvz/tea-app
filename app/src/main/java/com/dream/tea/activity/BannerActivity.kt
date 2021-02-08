package com.dream.tea.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.dream.tea.R
import kotlinx.android.synthetic.main.banner_card.*

class BannerActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.banner_card)
        bannerWebView.settings.javaScriptEnabled = true
        bannerWebView.webViewClient = WebViewClient()
        val linkUrl = intent.getStringExtra("linkUrl")
        if (linkUrl != null) {
            bannerWebView.loadUrl(linkUrl)
        }
    }

    companion object {
        fun actionStart(context: Context, linkUrl: String) {
            if (!TextUtils.isEmpty(linkUrl)) {
                val intent = Intent(context, BannerActivity::class.java)
                intent.putExtra("linkUrl", linkUrl)
                context.startActivity(intent)
            }
        }
    }
}
