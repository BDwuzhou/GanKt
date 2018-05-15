package top.bdwuzhou.gankt.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.model.Constant
import top.bdwuzhou.gankt.model.GankApi
import top.bdwuzhou.gankt.model.GankData
import top.bdwuzhou.gankt.model.Response

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv_test)
    lateinit var mTvTest: TextView

    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        unbinder = ButterKnife.bind(this)

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build()

        val gankApi = retrofit.create(GankApi::class.java)

        gankApi.android(10, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: Response<List<GankData>>? -> mTvTest.text = t.toString() },
                        { t: Throwable? ->
                            Toast.makeText(this, t?.cause.toString(), Toast.LENGTH_SHORT).show() })
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }
}
