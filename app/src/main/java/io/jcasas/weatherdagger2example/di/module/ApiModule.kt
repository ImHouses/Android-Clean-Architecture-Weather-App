package io.jcasas.weatherdagger2example.di.module

import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.data.source.external.WeatherApi
import io.jcasas.weatherdagger2example.data.source.external.WeatherService

/**
 * Created by jcasas on 8/11/17.
 */
@Module(includes = arrayOf(WeatherServiceModule::class))
class ApiModule {

    @Provides
    fun provideApi(weatherService:WeatherService):WeatherApi {
        return WeatherApi(weatherService)
    }
}