package io.jcasas.weatherdagger2example.data.weather

import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import javax.inject.Inject

class WeatherRepository @Inject constructor(
        private val dataSource: WeatherDataSource
) {

    suspend fun getCurrent(coordinates: Coordinates): WeatherEntity =
            dataSource.getCurrent(coordinates)

    suspend fun getOneWeekForecast(coordinates: Coordinates): List<ForecastEntity> =
            dataSource.getForecast(coordinates)
}