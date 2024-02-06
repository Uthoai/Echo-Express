package com.top.best.ecommerce.echoexpress.core

sealed class DataState<T>(
    var message: String? = null,
    var data: T? = null) {

    class Loading<T>: DataState<T>()

    class Success<T>(data: T?): DataState<T>(null,data)

    class Error<T>(message: String?): DataState<T>(message,null)

}