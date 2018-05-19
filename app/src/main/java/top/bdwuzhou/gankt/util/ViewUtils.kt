package top.bdwuzhou.gankt.util

import android.app.Activity
import android.app.Fragment
import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

//infix关键字提供中缀调用扩展函数

infix fun <T : View> View.findViewById(@IdRes id: Int): T = findViewById(id)

infix fun <T : View> Activity.findViewById(@IdRes id: Int): T = findViewById(id)

infix fun <T : View> Fragment.findViewById(@IdRes id: Int): T = activity.findViewById(id)

infix fun <T : View> android.support.v4.app.Fragment.findViewById(@IdRes id: Int): T? = activity?.findViewById(id)

infix fun <T : View> RecyclerView.ViewHolder.findViewById(@IdRes id: Int): T = itemView.findViewById(id)

