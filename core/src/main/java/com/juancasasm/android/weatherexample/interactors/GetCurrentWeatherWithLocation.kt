package com.juancasasm.android.weatherexample.interactors

import com.juancasasm.android.weatherexample.data.weather.WeatherRepository
import com.juancasasm.android.weatherexample.domain.Coordinates
import com.juancasasm.android.weatherexample.domain.Weather

class GetCurrentWeatherWithLocation(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(coordinates: Coordinates): Weather =
            weatherRepository.getCurrent(coordinates)
}