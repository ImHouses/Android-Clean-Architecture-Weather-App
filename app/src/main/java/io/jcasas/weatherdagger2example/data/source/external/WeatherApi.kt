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

package io.jcasas.weatherdagger2example.data.source.external

import com.juancasasm.android.weatherexample.domain.ForecastResponse
import com.juancasasm.android.weatherexample.domain.WeatherResponse
import io.jcasas.weatherdagger2example.util.Constants
import retrofit2.Call

/**
 * Created by jcasas on 8/10/17.
 */
class WeatherApi(private val weatherService: WeatherService) {

    fun getCurrentWeather(lat:Double, lon:Double): Call<WeatherResponse> {
        return weatherService.getCurrentWeather(lat, lon, Constants.API_KEY)
    }

    fun getCurrentForecast(lat:Double, lon:Double): Call<ForecastResponse> {
        return weatherService.get5dayForecast(lat, lon, 6, Constants.API_KEY)
    }
}