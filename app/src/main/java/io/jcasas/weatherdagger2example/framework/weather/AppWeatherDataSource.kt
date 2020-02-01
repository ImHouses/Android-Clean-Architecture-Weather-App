package io.jcasas.weatherdagger2example.framework.weather

import android.content.SharedPreferences
import android.net.ConnectivityManager
import io.jcasas.weatherdagger2example.data.exceptions.ConnectivityException
import io.jcasas.weatherdagger2example.data.weather.WeatherDataSource
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.forecast.ForecastType
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import io.jcasas.weatherdagger2example.framework.AppDatabase
import io.jcasas.weatherdagger2example.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppWeatherDataSource @Inject constructor(
        private val weatherService: WeatherService,
        private val appDatabase: AppDatabase
) : WeatherDataSource {

    private val key: String = Constants.API_KEY


    // TODO: Move some code to the WeatherRepository, logic with data should be there.
    // This class should only have functions for retrieving data from a source.
    /*suspend fun getCurrent(coordinates: Coordinates): WeatherEntity {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = sharedPreferences.getLong(Constants.Keys.WEATHER_LAST_UPDATE, currentTime)
        val timeDifference = currentTime - lastUpdated
        val weatherDao = appDatabase.weatherDao()
        val databaseCount = weatherDao.getCount("current")
        if (databaseCount == 0 && !isConnected()) {
            throw ConnectivityException()
        }
        if ((isConnected() && timeDifference > threshold) || (isConnected() && databaseCount == 0)) {
            val units = getUnitsForRequest()
            val weatherEntity =
                    weatherService.getCurrentWeather(coordinates.lat, coordinates.lon, key, units)
            weatherDao.insertWeather(WeatherRoomEntity("current", weatherEntity))
            sharedPreferences.edit().putLong(Constants.Keys.WEATHER_LAST_UPDATE, currentTime).apply()
        }
        val savedWeather = weatherDao.getWeather("current")
        /* TODO: Units conversion in case there is a mismatch between the weather ones and the config ones.*/
        return savedWeather.weatherEntity
    }*/

    /*suspend fun getForecast(coordinates: Coordinates): List<ForecastEntity> {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = sharedPreferences.getLong(Constants.Keys.WEATHER_LAST_UPDATE, currentTime)
        val timeDifference = currentTime - lastUpdated
        val forecastDao = appDatabase.forecastDao()
        val databaseCount = forecastDao.getCountByTypeAndLocation(ForecastType.FIVE_DAYS.value, "current")
        if (databaseCount == 0 && !isConnected()) {
            throw ConnectivityException()
        }
        if ((isConnected() && timeDifference > threshold) || (isConnected() && databaseCount == 0)) {
            val units = getUnitsForRequest()
            val forecastList =
                    weatherService.get5dayForecast(coordinates.lat, coordinates.lon, key, units).list
            forecastDao.deleteAllByTypeAndLocation(ForecastType.FIVE_DAYS.value, "current")
            for (forecastEntity in forecastList) {
                forecastDao.insert(ForecastRoomEntity(
                        null,
                        "current",
                        forecastEntity,
                        ForecastType.FIVE_DAYS
                ))
            }
            sharedPreferences.edit().putLong(Constants.Keys.WEATHER_LAST_UPDATE, currentTime).apply()
        }
        val savedForecastList = forecastDao.getByTypeAndLocation(ForecastType.FIVE_DAYS.value, "current")
        return savedForecastList.map { it.forecastEntity }
    }*/

    override suspend fun getCurrentWeatherFromLocal(
            units: Units
    ): WeatherEntity? = if (appDatabase.weatherDao().getCount("current") == 0) {
        null
    } else {
        appDatabase.weatherDao().getWeather("current").weatherEntity
    }


    override suspend fun getCurrentWeatherFromService(
            coordinates: Coordinates,
            units: Units
    ): WeatherEntity = weatherService.getCurrentWeather(
            coordinates.lat,
            coordinates.lon,
            key,
            units.value
    )

    override suspend fun getCurrent5DayForecastFromLocal(units: Units): List<ForecastEntity> {
        val forecastDao = appDatabase.forecastDao()
        val fiveDaysType = ForecastType.FIVE_DAYS.value
        return if (forecastDao.getCountByTypeAndLocation(fiveDaysType, "current") == 0) {
            listOf()
        } else {
            val savedForecasts = forecastDao.getByTypeAndLocation(fiveDaysType, "current")
            return savedForecasts.map { it.forecastEntity }
        }
    }

    override suspend fun getCurrent5DayForecastFromService(
            coordinates: Coordinates,
            units: Units
    ): List<ForecastEntity> = weatherService.get5dayForecast(
                coordinates.lon,
                coordinates.lat,
                key,
                units.value
        ).list

    override suspend fun saveCurrentWeatherToLocal(weatherEntity: WeatherEntity) {
        appDatabase
                .weatherDao()
                .insertWeather(WeatherRoomEntity("current", weatherEntity))
    }

    override suspend fun saveCurrent5DayForecastToLocal(forecastList: List<ForecastEntity>) {
        val forecastDao = appDatabase.forecastDao()
        forecastDao.deleteAllByTypeAndLocation(ForecastType.FIVE_DAYS.value, "current")
        for (forecast in forecastList) {
            forecastDao.insert(ForecastRoomEntity(
                    null,
                    "current",
                    forecast,
                    ForecastType.FIVE_DAYS
            ))
        }
    }


    // TODO: Change to a not deprecated implementation.
    /*private fun isConnected(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }*/
}