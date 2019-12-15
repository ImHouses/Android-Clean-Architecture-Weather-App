package io.jcasas.weatherdagger2example.di.module

import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.data.source.AppDataManager
import io.jcasas.weatherdagger2example.data.source.DataManager
import io.jcasas.weatherdagger2example.data.source.external.WeatherApi
import io.jcasas.weatherdagger2example.data.source.external.WeatherService

/**
 * Created by jcasas on 8/11/17.
 */
@Module(includes = arrayOf(AppModule::class))
class ApiModule {

    @Provides
    fun provideApi(weatherService: WeatherService): WeatherApi {
        return WeatherApi(weatherService)
    }

    @Provides
    fun provideDataManager(weatherApi: WeatherApi): DataManager {
        return AppDataManager(weatherApi)
    }
}