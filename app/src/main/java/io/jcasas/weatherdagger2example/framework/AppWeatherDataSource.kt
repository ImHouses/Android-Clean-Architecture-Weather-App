package io.jcasas.weatherdagger2example.framework

import com.juancasasm.android.weatherexample.data.weather.WeatherDataSource
import com.juancasasm.android.weatherexample.domain.Coordinates
import com.juancasasm.android.weatherexample.domain.Weather
import io.jcasas.weatherdagger2example.data.source.external.WeatherService
import io.jcasas.weatherdagger2example.util.Constants
import retrofit2.await

class AppWeatherDataSource(private val weatherService: WeatherService) : WeatherDataSource {

    private val key: String = Constants.API_KEY

    override suspend fun getCurrent(coordinates: Coordinates): Weather {
        // Any Exception must be thrown here..
        // TODO Handle exceptions.
        return weatherService.
                getCurrentWeather(coordinates.lat, coordinates.lon, key).
                await().
                weather[0]
    }
}