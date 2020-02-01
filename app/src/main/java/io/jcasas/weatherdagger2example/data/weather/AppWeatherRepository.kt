package io.jcasas.weatherdagger2example.data.weather

import io.jcasas.weatherdagger2example.data.config.ConfigurationDataSource
import io.jcasas.weatherdagger2example.data.exceptions.ConnectivityException
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.config.NetworkStatus
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import javax.inject.Inject

class AppWeatherRepository @Inject constructor(
        private val dataSource: WeatherDataSource,
        private val configurationDataSource: ConfigurationDataSource
): WeatherRepository {

    /* 3 hours. */
    private val threshold: Long = 3 * 60 * 60 * 1000
        get() = field

    override suspend fun getCurrentWeather(coordinates: Coordinates): WeatherEntity {
        val networkStatus = configurationDataSource.getNetworkStatus()
        val savedUnits = configurationDataSource.getConfiguration().defaultUnits
        val savedWeather = dataSource.getCurrentWeatherFromLocal(savedUnits)
        if (savedWeather == null && networkStatus == NetworkStatus.NOT_CONNECTED) {
            throw ConnectivityException()
        }
        return dataSource.getCurrentWeatherFromService(
                coordinates,
                configurationDataSource.getConfiguration().defaultUnits
        )
    }

    override suspend fun get5DayCurrentForecast(coordinates: Coordinates): List<ForecastEntity> =
            dataSource.getCurrent5DayForecastFromService(
                    coordinates,
                    configurationDataSource.getConfiguration().defaultUnits
            )
}