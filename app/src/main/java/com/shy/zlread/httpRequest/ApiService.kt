package com.shy.zlread.httpRequest

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by zhangli on 2019/3/20.
 */
interface ApiService {
    @GET("test")
    fun getLoginInfo(): Observable<Response<ResponseBody>>

    @GET("top250")
    fun getMovies(@Query("start") start: Int, @Query("count") count: Int): Observable<Response<ResponseBody>>

    @FormUrlEncoded
    @POST("getToken.json")
    fun getRongToken(@Header("App-Key") appkey: String, @Header("Timestamp") timestamp: String, @Header("Nonce") Nonce: String, @Header("Signature") Signature: String,@Header("Content-Type") content_type: String, @Field("userId") userId: String, @Field("name") userName: String, @Field("portraitUri") portraitUri: String): Observable<Response<ResponseBody>>
}