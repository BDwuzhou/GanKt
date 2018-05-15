package top.bdwuzhou.gankt.model

data class Response<T>(var results: T, var error: Boolean)