package top.bdwuzhou.gankt.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GankApi {
    /**
     * 福利
     */
    @GET("data/福利/{count}/{index}")
    fun welfare(@Path("count") count: Int, @Path("index") pageIndex: Int): Observable<Response<List<GankData>>>

    /**
     * Android
     */
    @GET("data/Android/{count}/{index}")
    fun android(@Path("count") count: Int, @Path("index") pageIndex: Int): Observable<Response<List<GankData>>>

    /**
     * iOS
     */
    @GET("data/iOS/{count}/{index}")
    fun ios(@Path("count") count: Int, @Path("index") pageIndex: Int): Observable<Response<List<GankData>>>

    /**
     * 休息视频
     */
    @GET("data/休息视频/{count}/{index}")
    fun restVideo(@Path("count") count: Int, @Path("index") pageIndex: Int): Observable<Response<List<GankData>>>

    /**
     * 拓展资源
     */
    @GET("data/拓展资源/{count}/{index}")
    fun expandRes(@Path("count") count: Int, @Path("index") pageIndex: Int): Observable<Response<List<GankData>>>

    /**
     * 前端
     */
    @GET("data/前端/{count}/{index}")
    fun frontEndWeb(@Path("count") count: Int, @Path("index") pageIndex: Int): Observable<Response<List<GankData>>>

    /**
     * 所有
     */
    @GET("data/all/{count}/{index}")
    fun all(@Path("count") count: Int, @Path("index") pageIndex: Int): Observable<Response<List<GankData>>>

    /**
     * 每日数据
     */
    @GET("day/{year}/{month}/{day}")
    fun day(@Path("year") year: Int, @Path("month") month: Int, @Path("day") day: Int): Observable<Response<List<GankData>>>

    /**
     * 随机数据     福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     */
    @GET("data/{kind}/{count}")
    fun random(@Path("kind") kind: String, @Path("count") count: Int): Observable<Response<List<GankData>>>
}