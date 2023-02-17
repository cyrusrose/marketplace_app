package com.it.access.util

sealed class Response<T>(val data: T? = null, val error: String? = null) {
    class Loading<T>: Response<T>()
    class Success<T>(data: T): Response<T>(data)
    class Failure(error: String): Response<Any>(error = error)
}