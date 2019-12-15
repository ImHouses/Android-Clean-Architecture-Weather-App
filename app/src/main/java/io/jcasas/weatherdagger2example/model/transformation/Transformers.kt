package io.jcasas.weatherdagger2example.model.transformation

import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import io.jcasas.weatherdagger2example.model.Weather

/**
 * Object to perform a transformation between Business Layer objects and App specific models.
 */
object Transformers {

    fun fromDomainWeather(weather: WeatherEntity): Weather = Weather(
            weatherId = weather.weatherId,
            status = weather.status,
            statusDescription = weather.statusDescription.capitalize(),
            temperature = weather.temperature.toInt(),
            maxTemperature = weather.maxTemperature,
            minTemperature = weather.minTemperature,
            units = weather.units,
            locationName = weather.locationName,
            coordinates = weather.coordinates
    )
}