package top.bdwuzhou.gankt.model

import com.google.gson.annotations.SerializedName

data class DayData(var Android: List<GankData>,
                   var iOS: List<GankData>,
                   @SerializedName("休息视频") var restVideo: List<GankData>,
                   @SerializedName("拓展资源") var expandRes: List<GankData>,
                   @SerializedName("瞎推荐") var random: List<GankData>,
                   @SerializedName("福利") var welfare: List<GankData>)