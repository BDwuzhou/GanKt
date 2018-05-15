package top.bdwuzhou.gankt.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.model.GankData
import top.bdwuzhou.gankt.model.HttpManager

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv_test)
    lateinit var mTvTest: TextView

    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        unbinder = ButterKnife.bind(this)

        HttpManager.android(10, 1)
                .subscribe({ t: List<GankData>? -> mTvTest.text = t.toString() })
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }
}
