package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherRepository
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import javax.inject.Inject

class GetOneWeekForecast @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(): List<ForecastEntity> =
            weatherRepository.getOneWeekForecast(locationRepository.getCurrentLocation())
}