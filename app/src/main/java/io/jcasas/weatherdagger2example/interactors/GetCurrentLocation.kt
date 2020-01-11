package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.location.AppLocationRepository
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.ErrorHandler
import io.jcasas.weatherdagger2example.util.Resource
import io.jcasas.weatherdagger2example.util.tryOrHandle
import javax.inject.Inject

class GetCurrentLocation @Inject constructor(
        private val locationRepository: AppLocationRepository,
        private val errorHandler: ErrorHandler
) {

    suspend operator fun invoke(): Resource<Coordinates> = tryOrHandle(errorHandler) {
        locationRepository.getCurrentLocation()
    }
}