package io.jcasas.weatherdagger2example.framework

import androidx.room.Database
import androidx.room.RoomDatabase
import io.jcasas.weatherdagger2example.framework.weather.WeatherDao
import io.jcasas.weatherdagger2example.model.Weather

@Database(entities = [Weather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}