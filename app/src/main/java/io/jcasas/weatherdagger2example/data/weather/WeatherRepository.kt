package io.jcasas.weatherdagger2example.data.weather

import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity

interface WeatherRepository {

    // TODO: Create an entity that contains both WeatherEntity and the List of the ForecastEntity.

    suspend fun getCurrentWeather(coordinates: Coordinates): WeatherEntity

    suspend fun get5DayCurrentForecast(coordinates: Coordinates): List<ForecastEntity>
}