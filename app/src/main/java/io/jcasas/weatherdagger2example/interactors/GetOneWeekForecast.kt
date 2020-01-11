package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.location.AppLocationRepository
import io.jcasas.weatherdagger2example.data.weather.AppWeatherRepository
import io.jcasas.weatherdagger2example.domain.ErrorHandler
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.util.Resource
import io.jcasas.weatherdagger2example.util.tryOrHandle
import javax.inject.Inject

class GetOneWeekForecast @Inject constructor(
        private val weatherRepository: AppWeatherRepository,
        private val locationRepository: AppLocationRepository,
        private val errorHandler: ErrorHandler
) {

    suspend operator fun invoke(): Resource<List<ForecastEntity>> = tryOrHandle(errorHandler) {
        weatherRepository.getOneWeekForecast(locationRepository.getCurrentLocation())
    }
}