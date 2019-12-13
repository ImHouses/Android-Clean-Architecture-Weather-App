package com.juancasasm.android.weatherexample.data.location

import com.juancasasm.android.weatherexample.domain.Coordinates

interface LocationDataSource {

    suspend fun getCurrent(): Coordinates
}