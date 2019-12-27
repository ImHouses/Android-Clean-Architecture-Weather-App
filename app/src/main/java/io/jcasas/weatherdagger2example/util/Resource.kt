package io.jcasas.weatherdagger2example.util

/**
 * Class for passing data and information about a request or operation made on the data layer
 * of the app.
 *
 * @param data The data to be passed from the data layer to the presentation layer.
 * @param message A message to inform about the operation.
 * @param exception An Exception to pass in case an error occurs on the data layer side.
 */
class Resource<T>(val data: T?, val message: String?, val exception: Exception?) {

    companion object {
        /**
         * Function that indictates a success.
         *
         * @param data The data to pass to the presentation layer.
         * @param message The message to pass to the presentation layer.
         */
        fun <T> success(data: T?, message: String? = null): Resource<T> =
                Resource(data, message, null)

        fun <T> error(data: T?, message: String, exception: Exception): Resource<T> =
                Resource(data, message, exception)

        fun <T> loading(data: T? = null, message: String? = null): Resource<T> =
                Resource(data, message, null)
    }
}