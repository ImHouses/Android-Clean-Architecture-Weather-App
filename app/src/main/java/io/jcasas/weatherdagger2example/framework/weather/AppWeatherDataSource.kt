package io.jcasas.weatherdagger2example.framework.weather

import android.content.SharedPreferences
import android.net.ConnectivityManager
import io.jcasas.weatherdagger2example.data.exceptions.ConnectivityException
import io.jcasas.weatherdagger2example.data.weather.WeatherDataSource
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import io.jcasas.weatherdagger2example.framework.AppDatabase
import io.jcasas.weatherdagger2example.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppWeatherDataSource @Inject constructor(
        private val weatherService: WeatherService,
        private val sharedPreferences: SharedPreferences,
        private val appDatabase: AppDatabase,
        private val connectivityManager: ConnectivityManager
) : WeatherDataSource {

    private val key: String = Constants.API_KEY
    /* 3 hours. */
    private val threshold: Long = 3 * 60 * 60 * 10000

    override suspend fun getCurrent(coordinates: Coordinates): WeatherEntity {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = sharedPreferences.getLong(Constants.Keys.WEATHER_LAST_UPDATE, currentTime)
        val timeDifference = currentTime - lastUpdated
        val weatherDao = appDatabase.weatherDao()
        if (weatherDao.getCount("current") == 0 && !isConnected()) {
            throw ConnectivityException()
        }
        if (isConnected() && timeDifference < threshold) {
            val units = getUnitsForRequest()
            val weatherEntity =
                    weatherService.getCurrentWeather(coordinates.lat, coordinates.lon, key, units)
            weatherDao.insertWeather(WeatherRoomEntity("current", weatherEntity))
        }
        val savedWeather = weatherDao.getWeather("current")
        /* TODO: Units conversion in case there is a mismatch between the weather ones and the config ones.*/
        return savedWeather.weatherEntity
    }

    override suspend fun getForecast(coordinates: Coordinates): List<ForecastEntity> {
        val units = getUnitsForRequest()
        return weatherService.get5dayForecast(coordinates.lat, coordinates.lon, key, units).list
    }

    private fun getUnitsForRequest(): String {
        val savedUnits =
                sharedPreferences.getString(Constants.Keys.UNITS_KEY, Constants.Values.UNITS_SI)
        return if (savedUnits == Constants.Values.UNITS_SI) "metric" else "imperial"
    }


    private fun isConnected(): Boolean {
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}