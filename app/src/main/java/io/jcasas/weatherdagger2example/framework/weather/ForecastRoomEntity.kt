package io.jcasas.weatherdagger2example.framework.weather

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.forecast.ForecastType

@Entity(tableName = "forecasts")
data class ForecastRoomEntity(
        @PrimaryKey(autoGenerate = true)
        var forecastId: Int?,
        val locationId: String,
        @Embedded
        val forecastEntity: ForecastEntity,
        val type: ForecastType
)