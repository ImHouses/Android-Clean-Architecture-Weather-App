package io.jcasas.weatherdagger2example.data.location

import io.jcasas.weatherdagger2example.domain.Coordinates

interface LocationRepository {

    suspend fun getCurrentLocation(): Coordinates
}