package io.jcasas.weatherdagger2example.util

import com.juancasasm.android.weatherexample.domain.WeatherResponse

/**
 * Created by jcasas on 9/30/17.
 */
interface WeatherCallback {

    fun onWeatherRetrieve(response: WeatherResponse?)
}