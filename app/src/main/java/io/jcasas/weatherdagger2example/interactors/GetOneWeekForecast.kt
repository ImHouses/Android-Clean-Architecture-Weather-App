package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherRepository
import io.jcasas.weatherdagger2example.domain.ErrorHandler
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.util.Resource
import io.jcasas.weatherdagger2example.util.tryOrHandle
import javax.inject.Inject

class GetOneWeekForecast @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val locationRepository: LocationRepository,
        private val errorHandler: ErrorHandler
) {

    suspend operator fun invoke(): Resource<List<ForecastEntity>> = tryOrHandle(errorHandler) {
        weatherRepository.getOneWeekForecast(locationRepository.getCurrentLocation())
    }
}