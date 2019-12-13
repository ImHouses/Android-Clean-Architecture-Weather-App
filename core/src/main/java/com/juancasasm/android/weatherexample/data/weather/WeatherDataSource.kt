package com.juancasasm.android.weatherexample.data.weather

import com.juancasasm.android.weatherexample.domain.Coordinates
import com.juancasasm.android.weatherexample.domain.Weather

interface WeatherDataSource {

    suspend fun getCurrent(coordinates: Coordinates): Weather

}