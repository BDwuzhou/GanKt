package top.bdwuzhou.gankt.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.model.GankData
import top.bdwuzhou.gankt.model.GlideApp

class MainAdapter(var data: List<GankData>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_main, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(holder.mIvItem.context)
                .load(data[position].url)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.mIvItem)
        holder.mTvDesc.text = data[position].desc
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mIvItem: ImageView = itemView.findViewById(R.id.iv_item)
        var mTvDesc: TextView = itemView.findViewById(R.id.tv_desc)
    }
}