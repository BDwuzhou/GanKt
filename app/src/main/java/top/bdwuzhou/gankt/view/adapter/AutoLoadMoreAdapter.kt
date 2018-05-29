package top.bdwuzhou.gankt.view.adapter

import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import top.bdwuzhou.gankt.util.GlideApp


abstract class AutoLoadMoreAdapter<T>(@LayoutRes private val layoutId: Int) :
        RecyclerView.Adapter<AutoLoadMoreAdapter<T>.ViewHolder>() {
    var loadMore: LoadMore? = null
    var onItemClickListener: OnItemClickListener<T>? = null
    var data: MutableList<T> = mutableListOf()
        set(value) = if (!data.isEmpty()) {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = data.size

                override fun getNewListSize(): Int = value.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return itemsTheSame(data[oldItemPosition], value[newItemPosition])
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return contentsTheSame(data[oldItemPosition], value[newItemPosition])
                }

            })
            field.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        } else {
            field = value
            notifyItemRangeInserted(0, data.size)
        }

    infix fun flushData(data: List<T>) {
        this.data.clear()
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

    infix fun updateData(data: List<T>) {
        this.data.addAll(data)
        notifyItemRangeInserted(this.data.size, this.data.size)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { view: View ->
            onItemClickListener?.onItemClick(view, data[position], position)
        }
        bindHolder(holder, data[position], position)
    }

    infix fun attachToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = this
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //拿到最后一条的position
                val layoutManager = recyclerView?.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    val endCompletelyPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    if (endCompletelyPosition == this@AutoLoadMoreAdapter.itemCount - 1) {
                        //执行加载更多的方法，无论是用接口还是别的方式都行
                        loadMore?.load()
                    }
                }
            }
        })
    }

    abstract fun itemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun contentsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun bindHolder(holder: ViewHolder, item: T, position: Int)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        infix fun getView(@IdRes id: Int): View = itemView.findViewById(id)
        infix fun getViewGroup(@IdRes id: Int): ViewGroup = itemView.findViewById(id) as ViewGroup
        infix fun getImageView(@IdRes id: Int): ImageView = itemView.findViewById(id) as ImageView
        infix fun getTextView(@IdRes id: Int): TextView = itemView.findViewById(id) as TextView
        infix fun getButton(@IdRes id: Int): Button = itemView.findViewById(id) as Button
        infix fun getEditText(@IdRes id: Int): EditText = itemView.findViewById(id) as EditText
        infix fun getProgressBar(@IdRes id: Int): ProgressBar = itemView.findViewById(id) as ProgressBar
        infix fun getCheckBox(@IdRes id: Int): CheckBox = itemView.findViewById(id) as CheckBox
        infix fun getRadioButton(@IdRes id: Int): RadioButton = itemView.findViewById(id) as RadioButton
        infix fun getLinearLayout(@IdRes id: Int): LinearLayout = itemView.findViewById(id) as LinearLayout
        infix fun getRelativeLayout(@IdRes id: Int): RelativeLayout = itemView.findViewById(id) as RelativeLayout
        infix fun getFrameLayout(@IdRes id: Int): FrameLayout = itemView.findViewById(id) as FrameLayout
        infix fun getConstraintLayout(@IdRes id: Int): ConstraintLayout = itemView.findViewById(id) as ConstraintLayout
        infix fun TextView.setText(text: CharSequence) {
            this.text = text
        }

        infix fun ImageView.setImage(url: String) {
            GlideApp.with(context).load(url).into(this)
        }

        infix fun ImageView.setImageRes(@DrawableRes id: Int) {
            this.setImageResource(id)
        }

        infix fun View.setOnClickListener(onClickListener: View.OnClickListener) {
            this.setOnClickListener(onClickListener)
        }
    }

    interface OnItemClickListener<T> {
        fun onItemClick(view: View, item: T, position: Int)
    }

    interface LoadMore {
        fun load()
    }
}
