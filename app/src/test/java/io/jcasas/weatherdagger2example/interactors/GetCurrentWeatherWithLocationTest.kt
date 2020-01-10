package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.exceptions.AppErrorHandler
import io.jcasas.weatherdagger2example.data.exceptions.LocationNullException
import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherRepository
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.ErrorEntity
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import io.jcasas.weatherdagger2example.util.Resource
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class GetCurrentWeatherWithLocationTest {

    private lateinit var SUT: GetCurrentWeatherWithLocation
    private val weatherRepo: WeatherRepo = WeatherRepo()
    private val locationRepo: LocationRepo = LocationRepo()

    @Before
    fun setup() {
        SUT = GetCurrentWeatherWithLocation(weatherRepo, locationRepo, AppErrorHandler())
    }

    // getCurrentWeather fails location
    @Test
    fun `getCurrentWeather failsLocation locationErrorEntityExpected`() {
        locationRepo.errorMode = true
        runBlocking {
            val value = SUT()
            assert(value is Resource.Error<WeatherEntity>)
            val errorEntity = value as Resource.Error<WeatherEntity>
            assert(errorEntity.errorEntity is ErrorEntity.LocationError)
            assert(errorEntity.errorEntity.originalException is LocationNullException)
        }
    }

    // getCurrentWeather fails call to web service
    @Test
    fun `getCurrentWeather failsCallToWebService serviceUnavailableErrorEntityExpected`() {
        weatherRepo.serviceErrorMode = true
        runBlocking {
            val value = SUT()
            assert(value is Resource.Error<WeatherEntity>)
            val errorEntity = (value as Resource.Error<WeatherEntity>).errorEntity
            assert(errorEntity is ErrorEntity.ServiceUnavailable)
            assert(errorEntity.originalException is SocketTimeoutException || errorEntity.originalException is IllegalArgumentException)
        }
    }

    // getCurrentWeather network unavailable Network Error Entity expected
    @Test
    fun `getCurrentWeather notNetwork networkErrorEntityExpected`() {
        weatherRepo.networkErrorMode = true
        runBlocking {
            val value = SUT()
            assert(value is Resource.Error<WeatherEntity>)
            val errorEntity = (value as Resource.Error<WeatherEntity>).errorEntity
            assert(errorEntity is ErrorEntity.Network)
            Assert.assertTrue(errorEntity.originalException is IOException)
        }
    }
    // getCurrentWeather succeeds
    @Test
    fun `getCurrentWeather success weatherEntityExpected`() {
        runBlocking {
            val value = SUT()
            assert(value is Resource.Success<WeatherEntity>)
            Assert.assertNotNull((value as Resource.Success).data)
        }
    }

    inner class WeatherRepo : WeatherRepository {

        var networkErrorMode: Boolean = false
        var serviceErrorMode: Boolean = false

        override suspend fun getCurrent(coordinates: Coordinates): WeatherEntity {
            if (networkErrorMode) {
                throw IOException()
            } else if (serviceErrorMode) {
                throw SocketTimeoutException()
            } else {
                return WeatherEntity(1, "Clouds", "Description", 40.1, 20.2, 42.0, Units.SI, "Mexico", Coordinates(0.0, 0.0), DateTime.now())
            }
        }

        override suspend fun getOneWeekForecast(coordinates: Coordinates): List<ForecastEntity> {
            return listOf()
        }
    }

    inner class LocationRepo : LocationRepository {

        var errorMode: Boolean = false

        override suspend fun getCurrentLocation(): Coordinates {
            if (errorMode) {
                throw LocationNullException()
            }
            return Coordinates(lat = 37.4219999, lon = -122.0862462)
        }
    }
}