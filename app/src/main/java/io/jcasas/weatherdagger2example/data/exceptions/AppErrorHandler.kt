package io.jcasas.weatherdagger2example.data.exceptions

import io.jcasas.weatherdagger2example.domain.ErrorEntity
import io.jcasas.weatherdagger2example.domain.ErrorHandler
import retrofit2.HttpException

class AppErrorHandler : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity = when (throwable) {
        is ConnectivityException -> ErrorEntity.Network(throwable)
        is LocationNullException -> ErrorEntity.LocationError(throwable)
        is HttpException -> ErrorEntity.Network(throwable)
        is IllegalArgumentException -> ErrorEntity.ServiceUnavailable(throwable)
        else -> ErrorEntity.Unknown(throwable)
    }
}