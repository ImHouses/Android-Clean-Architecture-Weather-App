package io.jcasas.weatherdagger2example.util

import io.jcasas.weatherdagger2example.data.source.model.WeatherResponse

/**
 * Created by jcasas on 9/30/17.
 */
interface WeatherCallback {

    fun onWeatherRetrieve(response:WeatherResponse?)
}