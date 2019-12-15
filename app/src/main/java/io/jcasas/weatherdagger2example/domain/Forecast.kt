package io.jcasas.weatherdagger2example.domain

import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity

/**
 * Created by jcasas on 10/6/17.
 */
class Forecast(val dt: Long, val weather: ArrayList<WeatherEntity>, val temp: Temperature)