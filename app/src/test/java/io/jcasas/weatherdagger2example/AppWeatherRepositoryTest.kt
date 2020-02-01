package io.jcasas.weatherdagger2example

import com.nhaarman.mockitokotlin2.*
import com.nhaarman.mockitokotlin2.internal.createInstance
import io.jcasas.weatherdagger2example.data.config.ConfigurationDataSource
import io.jcasas.weatherdagger2example.data.exceptions.ConnectivityException
import io.jcasas.weatherdagger2example.data.weather.AppWeatherRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherDataSource
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.config.Configuration
import io.jcasas.weatherdagger2example.domain.config.NetworkStatus
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import kotlin.reflect.KClass

class AppWeatherRepositoryTest {

    private lateinit var SUT: AppWeatherRepository
    private lateinit var weatherSrcMock: WeatherDataSource
    private lateinit var configSrcMock: ConfigurationDataSource
    private lateinit var weatherEntityMock: WeatherEntity
    private lateinit var forecastListMock: MutableList<ForecastEntity>
    private lateinit var configurationMock: Configuration

    @Before
    fun setup() {
        weatherSrcMock = mock()
        configSrcMock = mock()
        configurationMock = Configuration(Units.SI, System.currentTimeMillis())
        whenever(configSrcMock.getConfiguration()).thenReturn(configurationMock)
        weatherEntityMock = WeatherEntity(
                1,
                "",
                "",
                1.0,
                1.0,
                1.0,
                Units.IMPERIAL,
                "MX",
                Coordinates(1.1, -1.0),
                null
        )
        forecastListMock = mutableListOf(
                ForecastEntity(
                        1,
                        DateTime(),
                        1.1,
                        "",
                        "", 1.0, -1.0)
        )
        SUT = AppWeatherRepository(weatherSrcMock, configSrcMock)
    }

    @Test
    fun getCurrentWeather() {
        runBlocking {
            try {
                weatherSrcLocalEmpty()
                notConnected()
                SUT.getCurrentWeather(Coordinates(0.0, 0.0))
                verify(weatherSrcMock, times(1)).getCurrentWeatherFromLocal(any())
                verify(configSrcMock, times(1)).getConfiguration()
                verify(configSrcMock, times(1)).getNetworkStatus()
            } catch (exception: Exception) {
                assert(exception is ConnectivityException)
            }
            // TODO: Case when the time difference between the last update is greater than the threshold.
            // In that case, the repository should only check of internet connection
            // If there is internet connection then the repository gets the most recent
            // weather for the "current" location.
            // Then it must be saved to the local.
            // TODO: Case when at the end of the function the saved weather is returned.
        }
    }

    @Test
    fun get5DayCurrentForecast() {
        // TODO
    }

    private fun notConnected() {
        whenever(configSrcMock.getNetworkStatus()).thenReturn(NetworkStatus.NOT_CONNECTED)
    }

    private fun connected() {
        whenever(configSrcMock.getNetworkStatus()).thenReturn(NetworkStatus.CONNECTED)
    }

    private suspend fun weatherSrcLocalEmpty() {
        whenever(weatherSrcMock.getCurrentWeatherFromLocal(any())).thenReturn(null)
    }

    private suspend fun weatherSrcErrorFromService(exceptionClass: KClass<out Throwable>) {
        whenever(weatherSrcMock.getCurrentWeatherFromService(any(), any()))
                .thenThrow(createInstance(exceptionClass))
    }

    private suspend fun weatherSrcSuccessFromService() {
        whenever(weatherSrcMock.getCurrentWeatherFromService(any(), any()))
                .thenReturn(weatherEntityMock)
    }
}