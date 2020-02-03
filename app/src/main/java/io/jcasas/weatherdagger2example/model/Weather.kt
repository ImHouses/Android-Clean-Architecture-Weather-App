package io.jcasas.weatherdagger2example.model

import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.Units
import org.joda.time.DateTime

data class Weather(
        val weatherId: Int,
        val status: String,
        val statusDescription: String,
        val temperature: Int,
        val minTemperature: Double,
        val maxTemperature: Double,
        val units: Units,
        val locationName: String,
        val coordinates: Coordinates,
        val lastUpdate: DateTime?,
        val humidity: Int,
        val windSpeed: Double
)