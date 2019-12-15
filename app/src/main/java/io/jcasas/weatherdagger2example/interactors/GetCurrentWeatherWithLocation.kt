package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherRepository
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import javax.inject.Inject

class GetCurrentWeatherWithLocation @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(): WeatherEntity =
            weatherRepository.getCurrent(locationRepository.getCurrentLocation())
}