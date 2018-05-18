package top.bdwuzhou.gankt.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.model.GankApiManager
import top.bdwuzhou.gankt.model.GankData
import top.bdwuzhou.gankt.util.findViewById
import top.bdwuzhou.gankt.view.adapter.MainAdapter

class MainFragment : Fragment() {
    //    private val mRvList: RecyclerView by bindView(R.id.rv_list)
//    private val mRefresh: SwipeRefreshLayout by bindView(R.id.refresh)
    private var columnCount = 1
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mRvList: RecyclerView
    private lateinit var mRefresher: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mRvList = view findViewById R.id.rv_list
        mRefresher = view findViewById R.id.refresh
        initView()
        loadData()
        return view
    }

    //初始化 View
    private fun initView() {
        mainAdapter = MainAdapter()
        with(mRvList) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = mainAdapter
            itemAnimator = DefaultItemAnimator()
        }
        mRefresher.setOnRefreshListener { loadData() }
    }

    //加载数据
    private fun loadData() {
        GankApiManager.welfare(20, 1)
                .doOnSubscribe { mRefresher.isRefreshing = true }
                .subscribe({ it: List<GankData> ->
                    mainAdapter.data = it
                    mRefresher.isRefreshing = false
                }, { it ->
                    Log.e("testwuzhou", "=====>" + it.toString())
                })
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount: Int) = MainFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COLUMN_COUNT, columnCount)
            }
        }
    }
}
