package io.jcasas.weatherdagger2example.domain

import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity

data class ForecastResponse(val list: List<ForecastEntity>)