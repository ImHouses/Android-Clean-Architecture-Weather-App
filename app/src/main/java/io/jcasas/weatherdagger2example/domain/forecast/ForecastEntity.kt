package io.jcasas.weatherdagger2example.domain.forecast

import org.joda.time.DateTime

data class ForecastEntity(
        val id: Int,
        val date: DateTime,
        val temperature: Double,
        val status: String,
        val description: String,
        val maxTemperature: Double,
        val minTemperature: Double
)