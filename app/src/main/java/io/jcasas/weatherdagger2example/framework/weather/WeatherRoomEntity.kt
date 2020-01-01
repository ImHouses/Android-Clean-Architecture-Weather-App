package io.jcasas.weatherdagger2example.framework.weather

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity

@Entity(tableName = "weathers")
data class WeatherRoomEntity(
        @PrimaryKey
        val locationId: String,
        @Embedded
        val weatherEntity: WeatherEntity
)