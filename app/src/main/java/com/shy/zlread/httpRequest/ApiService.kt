package com.shy.zlread.httpRequest

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by zhangli on 2019/3/20.
 */
interface ApiService {
    @GET("test")
    fun getLoginInfo():Observable<Any>

    @GET("top250")
    fun getMovies(@Query("start") start :Int, @Query("count") count :Int): Observable<Any>
    @POST("today")
    fun getTodays():Observable<Any>
}