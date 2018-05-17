package top.bdwuzhou.gankt.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.model.GankData
import top.bdwuzhou.gankt.model.HttpManager
import top.bdwuzhou.gankt.view.adapter.MainAdapter

class MainActivity : AppCompatActivity() {

    @BindView(R.id.rv_list)
    lateinit var mRvList: RecyclerView

    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        unbinder = ButterKnife.bind(this)

        HttpManager.welfare(10, 1)
                .subscribe({ it: List<GankData> ->
                    mRvList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    mRvList.adapter = MainAdapter(it)
                },{
                    it -> Log.e("testwuzhou", "=====>" + it.cause.toString())
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }
}
