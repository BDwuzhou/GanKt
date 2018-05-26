package top.bdwuzhou.gankt.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.model.GankApiManager
import top.bdwuzhou.gankt.model.GankData
import top.bdwuzhou.gankt.util.GlideApp
import top.bdwuzhou.gankt.util.findViewById
import top.bdwuzhou.gankt.util.logE
import top.bdwuzhou.gankt.util.toast
import top.bdwuzhou.gankt.view.adapter.AutoLoadMoreAdapter

class MainFragment : Fragment() {
    //    private val mRvList: RecyclerView by bindView(R.id.rv_list)
//    private val mRefresh: SwipeRefreshLayout by bindView(R.id.refresh)
    private var columnCount = 1
    //    private lateinit var mMainAdapter: MainAdapter
    private lateinit var mMainAdapter: AutoLoadMoreAdapter<GankData>
    private lateinit var mRvList: RecyclerView
    private lateinit var mRefresher: SwipeRefreshLayout
    private val countPerPage: Int = 20
    private var pageIndex: Int = 1

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
        loadData(countPerPage, pageIndex)
        return view
    }

    //初始化 View
    private fun initView() {
        mMainAdapter = object : AutoLoadMoreAdapter<GankData>(R.layout.item_list_main) {
            override fun itemsTheSame(oldItem: GankData, newItem: GankData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun contentsTheSame(oldItem: GankData, newItem: GankData): Boolean {
                return oldItem.id == newItem.id && oldItem.url == newItem.url
            }

            override fun bindHolder(holder: ViewHolder, item: GankData, position: Int) {
                GlideApp.with(this@MainFragment)
                        .load(item.url)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(holder.getImageView(R.id.iv_item))
                holder.getTextView(R.id.tv_desc).text = item.desc
                holder.getTextView(R.id.tv_desc).setOnClickListener({ view ->
                    Toast.makeText(this@MainFragment.context, "${view.id}", Toast.LENGTH_SHORT).show()
                })
            }
        }
        with(mMainAdapter) {
            //给recyclerView设置adapter
            this attachToRecyclerView mRvList
            //自动加载
            loadMore = object : AutoLoadMoreAdapter.LoadMore {
                override fun load() {
                    loadData(countPerPage, ++pageIndex)
                }
            }
            //item点击事件
            onItemClickListener = object : AutoLoadMoreAdapter.OnItemClickListener<GankData> {
                override fun onItemClick(view: View, item: GankData, position: Int) {
                    Toast.makeText(view.context, "$position", Toast.LENGTH_SHORT).show()

                    this@MainFragment.context?.let { "$position".toast(it) }
                    "$position".toast(this@MainFragment)
                    toast("$position")
                }
            }
        }
        //对mRvList连续调用一系列方法
        with(mRvList) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            itemAnimator = DefaultItemAnimator()
        }
        with(mRefresher) {
            setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE)
            setOnRefreshListener { loadData(countPerPage, 1) }
        }
    }

    //加载数据
    private fun loadData(count: Int, index: Int) {
        GankApiManager.welfare(count, index)
                .doOnSubscribe { mRefresher.isRefreshing = true }
                .subscribe({ it: List<GankData> ->
                    if (index == 1) {
                        mMainAdapter flushData it.toMutableList()
                    } else {
                        mMainAdapter updateData it.toMutableList()
                    }
                    mRefresher.isRefreshing = false
                }, { it ->
                    logE("testwuzhou", "=====>$it")
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
