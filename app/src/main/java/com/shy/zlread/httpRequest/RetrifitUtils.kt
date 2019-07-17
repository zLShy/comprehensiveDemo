package com.shy.zlread.httpRequest

import android.text.TextUtils
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.shy.zlread.MyApplication
import com.shy.zlread.utils.LocalNetWorkUtils
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by zhangli on 2019/3/20.
 */
class RetrifitUtils {
    private var mApiService: ApiService? = null

    companion object {
        var baseUrl = "http://gank.io/api/"
    }

    fun getApiService(): ApiService{

        @Synchronized
        if (mApiService == null) {
            initRetrifit()
        }
        return mApiService!!
    }



    open fun initRetrifit() {

        val mIntercepter = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain?): Response {
                val request = chain!!.request()
                val response = chain!!.proceed(request)

                var cacheControl = request.cacheControl().toString()
                if (TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=60"
                }
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build()

            }

        }
//        //设置缓存
        val mCacheFile = File(MyApplication.getInatance().cacheDir,"response")
        val mCache = Cache(mCacheFile,1024*1024)
        val mClinet =  OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.SECONDS)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .cache(mCache)
                .addInterceptor(mIntercepter)
                .build()

       var mRetrofit = Retrofit.Builder().baseUrl(baseUrl)
                .client(mClinet)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        mApiService = mRetrofit.create(ApiService::class.java)
    }

    /**
     * 设缓存有效期为两天
     */
    private val CACHE_STALE_SEC = (60 * 60 * 24 * 2).toLong()

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private val mRewriteCacheControlInterceptor = Interceptor { chain ->
        var request = chain.request()
        val cacheControl = request.cacheControl().toString()
        if (!LocalNetWorkUtils.isNetConnected(MyApplication.getInatance())) {
            request = request.newBuilder()
                    .cacheControl(if (TextUtils.isEmpty(cacheControl))
                        CacheControl
                                .FORCE_NETWORK
                    else
                        CacheControl.FORCE_CACHE)
                    .build()
        }
        val originalResponse = chain.proceed(request)
        if (LocalNetWorkUtils.isNetConnected(MyApplication.getInatance())) {
            originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    //                        .addHeader("application/json", "charset=utf-8")
                    .removeHeader("Pragma")
                    .build()
        } else {
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    //                        .addHeader("application/json", "charset=utf-8")
                    .removeHeader("Pragma")
                    .build()
        }
    }
}