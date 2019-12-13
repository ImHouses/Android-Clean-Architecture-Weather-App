package com.juancasasm.android.weatherexample.data.location

import com.juancasasm.android.weatherexample.domain.Coordinates

class LocationRepository(private val locationDataSource: LocationDataSource) {

    suspend fun getCurrentLocation(): Coordinates = locationDataSource.getCurrent()
}