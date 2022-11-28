package com.reward.common

data class Response<T> private constructor(
    val content: T?
){
    companion object {
        fun<T> of(content: T ? = null): Response<T> = Response(content)
    }
}