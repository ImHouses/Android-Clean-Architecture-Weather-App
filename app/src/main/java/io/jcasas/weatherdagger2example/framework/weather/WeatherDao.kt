package io.jcasas.weatherdagger2example.framework.weather

import androidx.room.*
import io.jcasas.weatherdagger2example.model.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: Weather)

    @Query("SELECT * FROM weathers WHERE location_name = :locationName")
    suspend fun getWeather(locationName: String): Weather
}