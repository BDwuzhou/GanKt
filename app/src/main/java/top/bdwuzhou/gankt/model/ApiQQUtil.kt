package top.bdwuzhou.gankt.model

import java.net.URLEncoder
import java.security.MessageDigest
import java.util.*

/**
 * 获取接口签名
 */
fun getRequestSign(map: Map<String, String>): String {
    return MD5Url(addAppKeyToUrl(getUrlStr(sortMapByKey(map))))
}

/**
 * 将Map按key进行字典升序
 */
fun sortMapByKey(map: Map<String, String>): Map<String, String> {
    if (map.isEmpty()) {
        return map
    }
    val sortedMap = TreeMap<String, String>(Comparator { o1, o2 -> o1.compareTo(o2) })
    sortedMap.putAll(map)
    return sortedMap
}

/**
 * 拼接参数
 * 将排序后的参数列表value部分URL编码，URL编码用大写字母
 */
fun getUrlStr(map: Map<String, String>): String {
    var params = ""
    map.forEach({ key, value ->
        run { params += "$key=${URLEncoder.encode(value).toUpperCase()}&" }
    })
    return params
}

/**
 * 以app_key为键，将密钥拼接到URL编码后的字符串后面
 * voHvM6fnhWtnIbJf
 */
fun addAppKeyToUrl(encodedUrl: String): String {
    return "${encodedUrl}app_key=voHvM6fnhWtnIbJf"
}

/**
 * 对最终的URL进行MD5，使用大写
 */
fun MD5Url(urlWithKey: String): String {
    val digest = MessageDigest.getInstance("MD5")
    return digest.digest(urlWithKey.toByteArray(Charsets.UTF_8)).toString().toUpperCase()
}