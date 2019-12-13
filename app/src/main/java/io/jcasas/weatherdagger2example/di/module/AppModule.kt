/*
 * Copyright 2017, Juan Casas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jcasas.weatherdagger2example.di.module

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.juancasasm.android.weatherexample.data.location.LocationDataSource
import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.data.source.external.WeatherApi
import io.jcasas.weatherdagger2example.data.source.external.WeatherService
import io.jcasas.weatherdagger2example.di.ApplicationScope
import io.jcasas.weatherdagger2example.framework.AppLocationDataSource
import io.jcasas.weatherdagger2example.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by jcasas on 8/6/17.
 */
@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideRetrofit(gson: Gson):Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    fun provideGson(): Gson {
        // Any converters must be provided here.
        return GsonBuilder().create()
    }

    @Provides
    fun provideWeatherService(retrofit: Retrofit):WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationDataSource() : LocationDataSource = AppLocationDataSource(app)
}