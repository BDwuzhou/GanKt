package top.bdwuzhou.gankt.model

import com.google.gson.annotations.SerializedName

data class GankData(@SerializedName("_id") var id: String,
                    var createdAt: String?,
                    var desc: String?,
                    var publishedAt: String?,
                    var source: String?,
                    var type: String?,
                    var url: String?,
                    var isUsed: Boolean,
                    var who: String?,
                    var images: List<String>?)
