package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.domain.Coordinates
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(): Coordinates = locationRepository.getCurrentLocation()
}