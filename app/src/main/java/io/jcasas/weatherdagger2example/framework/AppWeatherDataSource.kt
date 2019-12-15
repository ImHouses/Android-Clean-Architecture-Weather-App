package io.jcasas.weatherdagger2example.framework

import android.content.SharedPreferences
import io.jcasas.weatherdagger2example.data.weather.WeatherDataSource
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import io.jcasas.weatherdagger2example.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppWeatherDataSource @Inject constructor(
        private val weatherService: WeatherService,
        private val sharedPreferences: SharedPreferences
) : WeatherDataSource {

    private val key: String = Constants.API_KEY

    override suspend fun getCurrent(coordinates: Coordinates): WeatherEntity {
        val savedUnits =
                sharedPreferences.getString(Constants.Keys.UNITS_KEY, Constants.Values.UNITS_SI)
        val units = if (savedUnits == Constants.Values.UNITS_SI) "metric" else "imperial"
        return weatherService.getCurrentWeather(coordinates.lat, coordinates.lon, key, units)
    }
}