package io.jcasas.weatherdagger2example.domain

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}