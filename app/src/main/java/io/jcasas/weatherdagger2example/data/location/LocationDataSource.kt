package io.jcasas.weatherdagger2example.data.location

import io.jcasas.weatherdagger2example.domain.Coordinates

interface LocationDataSource {

    suspend fun getCurrent(): Coordinates
}