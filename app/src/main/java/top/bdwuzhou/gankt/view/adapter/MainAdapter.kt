package top.bdwuzhou.gankt.view.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.model.GankData
import top.bdwuzhou.gankt.util.GlideApp
import top.bdwuzhou.gankt.util.bindView
import top.bdwuzhou.gankt.util.findViewById

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var mData: List<GankData>

    init {
        mData = arrayListOf()
    }

    var data: List<GankData>
        get() = mData
        set(value) {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = mData.size

                override fun getNewListSize(): Int = data.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = mData[oldItemPosition].id == data[newItemPosition].id

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldData = mData[oldItemPosition]
                    val newData = data[newItemPosition]
                    return oldData.id == newData.id && oldData.images?.equals(newData.images)!!
                }
            })
            mData = value
            result.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_main, parent, false))

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(holder.mIvItem.context)
                .load(mData[position].url)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.mIvItem)
        holder.mTvDesc.text = mData[position].desc
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Jake大神的Kotterknife
        val mIvItem: ImageView by bindView(R.id.iv_item)
        //中缀调用
        var mTvDesc: TextView = itemView findViewById R.id.tv_desc
    }
}