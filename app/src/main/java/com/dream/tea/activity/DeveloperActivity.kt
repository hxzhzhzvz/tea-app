package com.dream.tea.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dream.tea.R
import com.dream.tea.constants.RespCode
import com.dream.tea.utils.LogUtils
import com.dream.tea.service.AppSystemService
import com.dream.tea.service.ServiceCreator
import com.dream.tea.vo.resp.DeveloperRespVo
import com.dream.tea.vo.resp.RespResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developer)
        val textView: TextView = findViewById(R.id.developer_notes)
        val appSystemService = ServiceCreator.create<AppSystemService>()
        appSystemService.getDeveloperNotes()
            .enqueue(object : Callback<RespResult<DeveloperRespVo>> {
                override fun onFailure(call: Call<RespResult<DeveloperRespVo>>, t: Throwable) {
                    t.message?.let { LogUtils.e(TAG, it) }
                }

                override fun onResponse(
                    call: Call<RespResult<DeveloperRespVo>>,
                    response: Response<RespResult<DeveloperRespVo>>
                ) {
                    if (response.code() == RespCode.REQUEST_SUCCESS) {
                        val resp = response.body()
                        if (resp != null && resp.code == RespCode.SUCCESS_CODE) {
                            if (resp.data != null) {
                                textView.text = resp.data.content
                            }
                        }
                    }
                }
            })
    }

    companion object {

        private const val TAG: String = "DeveloperActivity"

        fun actionStart(context: Context) {
            val intent = Intent(context, DeveloperActivity::class.java)
            context.startActivity(intent)
        }
    }
}
