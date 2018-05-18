package top.bdwuzhou.gankt.model

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.bdwuzhou.gankt.util.BASE_URL

object GankApiManager {
    private val gankApi: GankApi = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(GankApi::class.java)

    /**
     * 应用线程调度
     */
    private fun applySchedule() = ObservableTransformer({ upstream: Observable<Response<List<GankData>>> ->
        upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    })

    /**
     * 去掉响应包裹类，得到结果
     */
    private fun applyMap() = ObservableTransformer({ upstream: Observable<Response<List<GankData>>> ->
        upstream.map { t -> t.results }
    })

    /**
     * 福利
     */
    fun welfare(count: Int, pageIndex: Int): Observable<List<GankData>> = gankApi.welfare(count, pageIndex)
            .compose(applySchedule())
            .compose(applyMap())

    /**
     * Android
     */
    fun android(count: Int, pageIndex: Int): Observable<List<GankData>> = gankApi.android(count, pageIndex)
            .compose(applySchedule())
            .compose(applyMap())

    /**
     * iOS
     */
    fun ios(count: Int, pageIndex: Int): Observable<List<GankData>> = gankApi.ios(count, pageIndex)
            .compose(applySchedule())
            .compose(applyMap())

    /**
     * 休息视频
     */
    fun restVideo(count: Int, pageIndex: Int): Observable<List<GankData>> = gankApi.restVideo(count, pageIndex)
            .compose(applySchedule())
            .compose(applyMap())

    /**
     * 拓展资源
     */
    fun expandRes(count: Int, pageIndex: Int): Observable<List<GankData>> = gankApi.expandRes(count, pageIndex)
            .compose(applySchedule())
            .compose(applyMap())

    /**
     * 前端
     */
    fun frontEndWeb(count: Int, pageIndex: Int): Observable<List<GankData>> = gankApi.frontEndWeb(count, pageIndex)
            .compose(applySchedule())
            .compose(applyMap())

    /**
     * 所有
     */
    fun all(count: Int, pageIndex: Int): Observable<List<GankData>> = gankApi.all(count, pageIndex)
            .compose(applySchedule())
            .compose(applyMap())

    /**
     * 每日数据
     */
    fun day(year: Int, month: Int, day: Int): Observable<DayData> = gankApi.day(year, month, day)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t -> t.results }

    /**
     * 随机
     */
    fun random(kind: String, count: Int): Observable<List<GankData>> = gankApi.random(kind, count)
            .compose(applySchedule())
            .compose(applyMap())
}