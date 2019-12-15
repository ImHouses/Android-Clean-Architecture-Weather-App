package io.jcasas.weatherdagger2example.data.location

import io.jcasas.weatherdagger2example.domain.Coordinates
import javax.inject.Inject

class LocationRepository @Inject constructor(
        private val locationDataSource: LocationDataSource
) {

    suspend fun getCurrentLocation(): Coordinates = locationDataSource.getCurrent()
}