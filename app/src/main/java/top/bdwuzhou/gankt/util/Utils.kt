package top.bdwuzhou.gankt.util

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast

/**
 * 反转布尔值
 */
fun Boolean.reverse(): Boolean {
    return !this
}

/**
 * Toast部分
 */
fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun String.toast(fragment: Fragment, duration: Int = Toast.LENGTH_SHORT) {
    fragment.context?.let { toast(it, duration) }
}

fun Fragment.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    content.toast(this, duration)
}

fun Activity.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    content.toast(this, duration)
}

/**
 * Log部分
 */
fun Any.logV(tag: String = this::class.java.simpleName, msg: String) {
    Log.v(tag, msg)
}

fun Any.logD(tag: String = this::class.java.simpleName, msg: String) {
    Log.d(tag, msg)
}

fun Any.logI(tag: String = this::class.java.simpleName, msg: String) {
    Log.i(tag, msg)
}

fun Any.logW(tag: String = this::class.java.simpleName, msg: String) {
    Log.w(tag, msg)
}

fun Any.logE(tag: String = this::class.java.simpleName, msg: String) {
    Log.e(tag, msg)
}