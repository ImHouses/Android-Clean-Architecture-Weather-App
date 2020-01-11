package io.jcasas.weatherdagger2example.data.location

import io.jcasas.weatherdagger2example.domain.Coordinates
import javax.inject.Inject

class AppLocationRepository @Inject constructor(
        private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun getCurrentLocation(): Coordinates = locationDataSource.getCurrent()
}