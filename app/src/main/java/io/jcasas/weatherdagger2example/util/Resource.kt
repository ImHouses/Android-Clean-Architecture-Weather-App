package io.jcasas.weatherdagger2example.util

import io.jcasas.weatherdagger2example.domain.ErrorEntity

/**
 * Class for passing data and information about a request or operation made on the data layer
 * of the app.
 *
 * @param data The data to be passed from the data layer to the presentation layer.
 * @param message A message to inform about the operation.
 * @param exception An Exception to pass in case an error occurs on the data layer side.
 */
sealed class Resource<T> {

    data class Success<T>(val data: T): Resource<T>()

    data class Error<T>(val errorEntity: ErrorEntity): Resource<T>()
}