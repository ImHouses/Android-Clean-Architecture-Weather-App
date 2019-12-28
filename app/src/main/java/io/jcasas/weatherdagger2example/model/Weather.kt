package io.jcasas.weatherdagger2example.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.Units

@Entity(tableName = "weathers")
data class Weather(
        @ColumnInfo(name = "weather_id")
        val weatherId: Int,
        val status: String,
        @ColumnInfo(name = "status_description")
        val statusDescription: String,
        val temperature: Int,
        @ColumnInfo(name = "min_temperature")
        val minTemperature: Double,
        @ColumnInfo(name = "max_temperature")
        val maxTemperature: Double,
        val units: Units,
        @PrimaryKey
        @ColumnInfo(name = "location_name")
        val locationName: String,
        val coordinates: Coordinates
)