package top.bdwuzhou.gankt.model

data class Response<T>(var error: Boolean, var results: T, var category: List<String>)