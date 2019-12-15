package io.jcasas.weatherdagger2example.di.module

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.jcasas.weatherdagger2example.data.location.LocationDataSource
import io.jcasas.weatherdagger2example.data.weather.WeatherDataSource
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.data.config.ConfigurationDataSource
import io.jcasas.weatherdagger2example.data.location.LocationRepository
import io.jcasas.weatherdagger2example.data.weather.WeatherRepository
import io.jcasas.weatherdagger2example.framework.WeatherService
import io.jcasas.weatherdagger2example.di.ApplicationScope
import io.jcasas.weatherdagger2example.framework.AppConfigDataSource
import io.jcasas.weatherdagger2example.framework.AppLocationDataSource
import io.jcasas.weatherdagger2example.framework.AppWeatherDataSource
import io.jcasas.weatherdagger2example.interactors.GetCurrentWeatherWithLocation
import io.jcasas.weatherdagger2example.model.deserializer.CurrentWeatherDeserializer
import io.jcasas.weatherdagger2example.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(
            currentWeatherDeserializer: CurrentWeatherDeserializer
    ): Gson {
        // Any converters must be provided here.
        return GsonBuilder()
                .registerTypeAdapter(WeatherEntity::class.java, currentWeatherDeserializer)
                .create()
    }

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationSource(appContext: Context): LocationDataSource =
            AppLocationDataSource(appContext)


    @Provides
    @Singleton
    fun provideWeatherSource(
            weatherService: WeatherService,
            sharedPreferences: SharedPreferences
    ): WeatherDataSource {
        return AppWeatherDataSource(weatherService, sharedPreferences)
    }

    @Provides
    @Singleton
    fun providePreferencesSource(sharedPreferences: SharedPreferences): ConfigurationDataSource =
            AppConfigDataSource(sharedPreferences)
}