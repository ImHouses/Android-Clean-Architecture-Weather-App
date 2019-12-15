package io.jcasas.weatherdagger2example.data.weather

import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity

interface WeatherDataSource {

    suspend fun getCurrent(coordinates: Coordinates): WeatherEntity

}