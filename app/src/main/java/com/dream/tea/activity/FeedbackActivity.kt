package com.dream.tea.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dream.tea.R
import com.dream.tea.constants.App
import com.dream.tea.utils.LogUtils
import com.dream.tea.service.AppSystemService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.vo.req.FeedbackReqVo
import com.dream.tea.vo.resp.RespResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback)
        val context = this
        val feedbackEditText: EditText = findViewById(R.id.feedback_edit_text)
        val feedbackButton: Button = findViewById(R.id.submit_feedback)
        feedbackButton.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle(R.string.tips)
                setMessage("确定提交该反馈内容吗？")
                setCancelable(false)
                setPositiveButton(R.string.confirm) { _, _ ->
                    try {
                        val feedbackValue = feedbackEditText.text.toString()
                        if (!TextUtils.isEmpty(feedbackValue)) {
                            val appSystemService = ServiceCreator.create<AppSystemService>()
                            val reqVo = FeedbackReqVo()
                            reqVo.content = feedbackValue
                            appSystemService.feedback(reqVo)
                                .enqueue(object : Callback<RespResult<Void>> {
                                    override fun onFailure(
                                        call: Call<RespResult<Void>>,
                                        t: Throwable
                                    ) {
                                        t.message?.let { it1 -> LogUtils.e(TAG, it1) }
                                    }

                                    override fun onResponse(
                                        call: Call<RespResult<Void>>,
                                        response: Response<RespResult<Void>>
                                    ) {
                                        // do nothing
                                    }
                                })
                        }
                    } catch (e: Exception) {
                        e.message?.let { it1 -> LogUtils.e(TAG, it1) }
                    }
                    Toast.makeText(context, "反馈成功", Toast.LENGTH_LONG).show()
                    feedbackEditText.text.clear()
                }
                setNegativeButton(R.string.cancel) { _, _ ->
                }
            }.show()
        }
    }

    override fun onStart() {
        super.onStart()
        if (TextUtils.isEmpty(App.token)) {
            LoginActivity.actionStart(this)
        }
    }

    companion object {

        private const val TAG = "FeedbackActivity"

        fun actionStart(context: Context) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context.startActivity(intent)
        }
    }
}
