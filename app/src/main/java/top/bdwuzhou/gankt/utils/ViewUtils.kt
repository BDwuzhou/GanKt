package top.bdwuzhou.gankt.utils

import android.app.Activity
import android.app.Fragment
import android.support.annotation.IdRes
import android.view.View

//提供中缀调用扩展函数

infix fun <T : View> View.findViewById(@IdRes id: Int): T = findViewById(id)

infix fun <T : View> Activity.findViewById(@IdRes id: Int): T = findViewById(id)

infix fun <T : View> Fragment.findViewById(@IdRes id: Int): T = activity.findViewById(id)

infix fun <T : View> android.support.v4.app.Fragment.findViewById(@IdRes id: Int): T? = activity?.findViewById(id)
