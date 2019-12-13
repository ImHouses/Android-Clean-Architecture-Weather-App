package com.juancasasm.android.weatherexample.data.weather

import com.juancasasm.android.weatherexample.domain.Coordinates
import com.juancasasm.android.weatherexample.domain.Weather

class WeatherRepository(private val dataSource: WeatherDataSource) {

    suspend fun getCurrent(coordinates: Coordinates): Weather = dataSource.getCurrent(coordinates)
}