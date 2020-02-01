package io.jcasas.weatherdagger2example.domain.config

import io.jcasas.weatherdagger2example.domain.Units

data class Configuration(
        val defaultUnits: Units,
        val lastCurrentWeatherUpdate: Long
)