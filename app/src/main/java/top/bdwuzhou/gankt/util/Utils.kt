package top.bdwuzhou.gankt.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.Fragment
import android.util.Base64
import android.util.Log
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.IOException


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

/**
 * bitmap与base64
 */
fun bitmapToBase64(bitmap: Bitmap): String {
    var result = ""
    val baos = ByteArrayOutputStream()
    try {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        baos.flush()
        baos.close()
        val bitmapBytes = baos.toByteArray()
        result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            baos.flush()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return result
}

/**
 * base64转为bitmap
 *
 * @param base64Data
 * @return
 */
fun base64ToBitmap(base64Data: String): Bitmap {
    val bytes = Base64.decode(base64Data, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}
