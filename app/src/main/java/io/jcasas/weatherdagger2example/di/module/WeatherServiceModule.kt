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

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.data.source.external.WeatherApi
import io.jcasas.weatherdagger2example.data.source.external.WeatherService
import io.jcasas.weatherdagger2example.di.ApplicationScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by jcasas on 8/6/17.
 */
@Module
class WeatherServiceModule(val mBaseUrl:String) {

    @Provides
    fun provideRetrofit():Retrofit {
        return Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    @Provides
    fun provideWeatherService(retrofit:Retrofit):WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

}