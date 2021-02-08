package com.dream.tea.service

import com.dream.tea.constants.App
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceCreator {

    /**
     * 访问的域名
     */
    //const val BASE_URL = "http://192.168.1.104:8050"
    private const val BASE_URL = "http://121.5.62.86"

    /**
     * tea服务的访问前缀
     */
    const val TEA_CONTEXT_PATH = "/tea"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(genericClient())
        .build()

    private fun genericClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request = chain.request()
                        .newBuilder()
                        .addHeader(App.tokenName, App.token)
                        .build()
                    return chain.proceed(request)
                }
            })
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}