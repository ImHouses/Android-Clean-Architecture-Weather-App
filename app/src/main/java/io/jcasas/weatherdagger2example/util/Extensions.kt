package io.jcasas.weatherdagger2example.util

import io.jcasas.weatherdagger2example.domain.ErrorHandler

suspend inline fun <T> tryOrHandle(errorHandler: ErrorHandler, body: ()->T): Resource<T> = try {
    Resource.Success(body())
} catch (throwable: Throwable) {
    throwable.printStackTrace()
    Resource.Error(errorHandler.getError(throwable))
}