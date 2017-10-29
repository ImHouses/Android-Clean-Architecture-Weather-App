package io.jcasas.weatherdagger2example.data.source

import io.jcasas.weatherdagger2example.data.source.model.ForecastResponse
import io.jcasas.weatherdagger2example.util.OnModelLoaded
import io.jcasas.weatherdagger2example.util.WeatherCallback

/**
 * Created by jcasas on 9/30/17.
 */
interface DataManager {

    fun getWeather(lat:Double, lon:Double, callback:WeatherCallback)

    fun getForecast(lat: Double, lon: Double, callback: OnModelLoaded<ForecastResponse>)

}