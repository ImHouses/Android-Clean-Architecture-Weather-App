package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.exceptions.AppErrorHandler
import io.jcasas.weatherdagger2example.data.exceptions.LocationNullException
import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherRepository
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.ErrorEntity
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.util.Resource
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.IOException
import java.net.SocketTimeoutException

class GetOneWeekForecastTest {


    private lateinit var SUT: GetOneWeekForecast
    private lateinit var weatherRepositoryMock: WeatherRepository
    private lateinit var locationRepositoryMock: LocationRepository

    @Before
    fun setup() {
        weatherRepositoryMock = Mockito.mock(WeatherRepository::class.java)
        locationRepositoryMock = Mockito.mock(LocationRepository::class.java)
        SUT = GetOneWeekForecast(weatherRepositoryMock, locationRepositoryMock, AppErrorHandler())
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    // getCurrentWeather fails location
    @Test
    fun `getCurrentWeather failsLocation locationErrorEntityExpected`() {
        runBlocking {
            `when`(locationRepositoryMock.getCurrentLocation()).thenAnswer { throw LocationNullException() }
            val value = SUT()
            assert(value is Resource.Error<List<ForecastEntity>>)
            val errorEntity = value as Resource.Error<List<ForecastEntity>>
            assert(errorEntity.errorEntity is ErrorEntity.LocationError)
            assert(errorEntity.errorEntity.originalException is LocationNullException)
        }
    }

    // getCurrentWeather fails call to web service
    @Test
    fun `getCurrentWeather failsCallToWebService serviceUnavailableErrorEntityExpected`() {
        runBlocking {
            locationSuccess()
            `when`(weatherRepositoryMock.getOneWeekForecast(any(Coordinates::class.java))).thenAnswer { throw SocketTimeoutException() }
            val value = SUT()
            assert(value is Resource.Error<List<ForecastEntity>>)
            val errorEntity = (value as Resource.Error<List<ForecastEntity>>).errorEntity
            assert(errorEntity is ErrorEntity.ServiceUnavailable)
            assert(errorEntity.originalException is SocketTimeoutException || errorEntity.originalException is IllegalArgumentException)
        }
    }

    // getCurrentWeather network unavailable Network Error Entity expected
    @Test
    fun `getCurrentWeather notNetwork networkErrorEntityExpected`() {
        runBlocking {
            locationSuccess()
            `when`(weatherRepositoryMock.getOneWeekForecast(any(Coordinates::class.java))).thenAnswer { throw IOException() }
            val value = SUT()
            assert(value is Resource.Error<List<ForecastEntity>>)
            val errorEntity = (value as Resource.Error<List<ForecastEntity>>).errorEntity
            assert(errorEntity is ErrorEntity.Network)
            assertTrue(errorEntity.originalException is IOException)
        }
    }
    // getCurrentWeather succeeds
    @Test
    fun `getCurrentWeather success weatherEntityExpected`() {
        runBlocking {
            locationSuccess()
            val expectedResult = listOf(ForecastEntity(1, DateTime.now(), 0.0, "Clouds", "Description", 10.0, 10.0))
            val expectedValue = expectedResult[0]
            `when`(weatherRepositoryMock.getOneWeekForecast(any(Coordinates::class.java))).thenAnswer { expectedResult }
            val value = SUT()
            assert(value is Resource.Success<List<ForecastEntity>>)
            assertNotNull((value as Resource.Success).data)
            assert(value.data.size == 1)
            assert(value.data[0] == expectedValue)
        }
    }

    private suspend fun locationSuccess() {
        `when`(locationRepositoryMock.getCurrentLocation()).thenAnswer { Coordinates(0.0, 0.0) }
    }
}