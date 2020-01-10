package io.jcasas.weatherdagger2example.di.module

import dagger.Binds
import dagger.Module
import io.jcasas.weatherdagger2example.data.location.AppLocationRepository
import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.data.weather.AppWeatherRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherRepository
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideWeatherRepo(weather: AppWeatherRepository): WeatherRepository

    @Binds
    @Singleton
    abstract fun provideLocationRepo(locationRepo: AppLocationRepository): LocationRepository
}