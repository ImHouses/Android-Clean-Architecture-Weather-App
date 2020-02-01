package io.jcasas.weatherdagger2example.data.weather

import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity

interface WeatherDataSource {

    suspend fun getCurrentWeatherFromLocal(units: Units): WeatherEntity?

    suspend fun saveCurrentWeatherToLocal(weatherEntity: WeatherEntity)

    suspend fun getCurrentWeatherFromService(coordinates: Coordinates, units: Units): WeatherEntity

    suspend fun getCurrent5DayForecastFromLocal(units: Units): List<ForecastEntity>

    suspend fun getCurrent5DayForecastFromService(coordinates: Coordinates, units: Units): List<ForecastEntity>

    suspend fun saveCurrent5DayForecastToLocal(forecastList: List<ForecastEntity>)
}