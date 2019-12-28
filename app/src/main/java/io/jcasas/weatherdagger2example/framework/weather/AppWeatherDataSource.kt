package io.jcasas.weatherdagger2example.framework.weather

import android.content.SharedPreferences
import io.jcasas.weatherdagger2example.data.weather.WeatherDataSource
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
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
        val units = getUnits()
        return weatherService.getCurrentWeather(coordinates.lat, coordinates.lon, key, units)
    }

    override suspend fun getForecast(coordinates: Coordinates): List<ForecastEntity> {
        val units = getUnits()
        return weatherService.get5dayForecast(coordinates.lat, coordinates.lon, key, units).list
    }

    private fun getUnits(): String {
        val savedUnits =
                sharedPreferences.getString(Constants.Keys.UNITS_KEY, Constants.Values.UNITS_SI)
        return if (savedUnits == Constants.Values.UNITS_SI) "metric" else "imperial"
    }
}