/*
 * Copyright 2019, Juan Casas
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