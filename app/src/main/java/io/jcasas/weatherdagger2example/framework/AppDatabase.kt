package io.jcasas.weatherdagger2example.framework

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.jcasas.weatherdagger2example.framework.weather.ForecastDao
import io.jcasas.weatherdagger2example.framework.weather.ForecastRoomEntity
import io.jcasas.weatherdagger2example.framework.weather.WeatherDao
import io.jcasas.weatherdagger2example.framework.weather.WeatherRoomEntity

@Database(entities = [WeatherRoomEntity::class, ForecastRoomEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    abstract fun forecastDao(): ForecastDao
}