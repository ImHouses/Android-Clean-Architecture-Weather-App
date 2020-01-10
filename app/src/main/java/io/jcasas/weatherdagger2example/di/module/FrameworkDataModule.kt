package io.jcasas.weatherdagger2example.di.module

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.jcasas.weatherdagger2example.data.location.LocationDataSource
import io.jcasas.weatherdagger2example.data.weather.WeatherDataSource
import io.jcasas.weatherdagger2example.domain.weather.WeatherEntity
import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.data.config.ConfigurationDataSource
import io.jcasas.weatherdagger2example.framework.weather.ForecastResponse
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.framework.AppDatabase
import io.jcasas.weatherdagger2example.framework.weather.WeatherService
import io.jcasas.weatherdagger2example.framework.config.AppConfigDataSource
import io.jcasas.weatherdagger2example.framework.location.AppLocationDataSource
import io.jcasas.weatherdagger2example.framework.weather.AppWeatherDataSource
import io.jcasas.weatherdagger2example.framework.deserializer.CurrentWeatherDeserializer
import io.jcasas.weatherdagger2example.framework.deserializer.ForecastDeserializer
import io.jcasas.weatherdagger2example.framework.deserializer.SingleWeekForecastResponseDeserializer
import io.jcasas.weatherdagger2example.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class FrameworkDataModule {

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
            currentWeatherDeserializer: CurrentWeatherDeserializer,
            forecastDeserializer: ForecastDeserializer,
            singleWeekForecastDeserializer: SingleWeekForecastResponseDeserializer
    ): Gson = GsonBuilder()
                .registerTypeAdapter(WeatherEntity::class.java, currentWeatherDeserializer)
                .registerTypeAdapter(ForecastEntity::class.java, forecastDeserializer)
                .registerTypeAdapter(ForecastResponse::class.java, singleWeekForecastDeserializer)
                .create()

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationSource(
            appContext: Context,
            @Named("internal_config") sharedPreferences: SharedPreferences
    ): LocationDataSource = AppLocationDataSource(appContext, sharedPreferences)


    @Provides
    @Singleton
    fun provideWeatherSource(
            weatherService: WeatherService,
            sharedPreferences: SharedPreferences,
            appDatabase: AppDatabase,
            connectivityManager: ConnectivityManager
    ): WeatherDataSource = AppWeatherDataSource(
            weatherService,
            sharedPreferences,
            appDatabase,
            connectivityManager
    )

    @Provides
    @Singleton
    fun providePreferencesSource(sharedPreferences: SharedPreferences): ConfigurationDataSource =
            AppConfigDataSource(sharedPreferences)

    @Provides
    @Singleton
    fun provideForecastDeserializer(): ForecastDeserializer = ForecastDeserializer()

    @Provides
    @Singleton
    fun provideOneWeekForecastDeserializer(): SingleWeekForecastResponseDeserializer =
            SingleWeekForecastResponseDeserializer()
}