package io.jcasas.weatherdagger2example.data.weather

import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity

interface WeatherRepository {

    suspend fun getCurrent(coordinates: Coordinates): WeatherEntity

    suspend fun getOneWeekForecast(coordinates: Coordinates): List<ForecastEntity>
}