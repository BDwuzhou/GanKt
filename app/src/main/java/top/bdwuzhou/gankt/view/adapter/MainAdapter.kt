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
    var data: List<GankData> = arrayListOf()
        set(value) = if (!data.isEmpty()) {
            field = value
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return data.size
                }

                override fun getNewListSize(): Int {
                    return value.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return data[oldItemPosition].id == value[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldData = data[oldItemPosition]
                    val newData = value[newItemPosition]
                    return oldData.id == newData.id && oldData.desc == newData.desc
                }
            }).dispatchUpdatesTo(this)
        } else {
            field = value
            notifyItemRangeInserted(0, data.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_main, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(holder.mIvItem)
                .load(data[position].url)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.mIvItem)
        holder.mTvDesc.text = data[position].desc
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Jake大神的Kotterknife
        val mIvItem: ImageView by bindView(R.id.iv_item)
        //中缀调用
        val mTvDesc: TextView = this findViewById R.id.tv_desc
    }
}
