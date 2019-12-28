package io.jcasas.weatherdagger2example.framework

import androidx.room.TypeConverter
import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.Units

/**
 * Class for defining converters for storing complex data types.
 */
class Converters {

    @TypeConverter
    fun unitsToString(units: Units): String = units.value

    @TypeConverter
    fun stringToUnits(value: String): Units = if (value == Units.IMPERIAL.value) {
        Units.IMPERIAL
    } else {
        Units.SI
    }

    @TypeConverter
    fun coordinatesToString(coordinates: Coordinates) = "${coordinates.lon},${coordinates.lat}"

    @TypeConverter
    fun stringToCoordinates(value: String): Coordinates {
        val values = value.split(",")
        return Coordinates(lon = values[0].toDouble(), lat = values[1].toDouble())
    }
}