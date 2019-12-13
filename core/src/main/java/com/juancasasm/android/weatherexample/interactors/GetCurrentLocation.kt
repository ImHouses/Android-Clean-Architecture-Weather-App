package com.juancasasm.android.weatherexample.interactors

import com.juancasasm.android.weatherexample.data.location.LocationRepository
import com.juancasasm.android.weatherexample.domain.Coordinates

class GetCurrentLocation(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(): Coordinates = locationRepository.getCurrentLocation()
}