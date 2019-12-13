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

package io.jcasas.weatherdagger2example.data.source

import io.jcasas.weatherdagger2example.data.source.external.WeatherApi
import com.juancasasm.android.weatherexample.domain.ForecastResponse
import com.juancasasm.android.weatherexample.domain.WeatherResponse
import io.jcasas.weatherdagger2example.util.OnModelLoaded
import io.jcasas.weatherdagger2example.util.WeatherCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by jcasas on 8/11/17.
 */
class AppDataManager(private val weatherApi: WeatherApi) : DataManager {

    override fun getWeather(lat: Double, lon: Double, callback: WeatherCallback) {
        weatherApi.getCurrentWeather(lat, lon).enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(p0: Call<WeatherResponse>?, p1: Throwable?) {
                callback.onWeatherRetrieve(null)
            }

            override fun onResponse(p0: Call<WeatherResponse>?, p1: Response<WeatherResponse>?) {
                callback.onWeatherRetrieve(p1!!.body())
            }
        })
    }

    override fun getForecast(lat: Double, lon: Double, callback: OnModelLoaded<ForecastResponse>) {
        weatherApi.getCurrentForecast(lat, lon).enqueue(object: Callback<ForecastResponse> {
            override fun onFailure(p0: Call<ForecastResponse>?, p1: Throwable?) {
                callback.onModelLoaded(null)
            }

            override fun onResponse(p0: Call<ForecastResponse>?, p1: Response<ForecastResponse>?) {
                callback.onModelLoaded(p1!!.body())
            }
        })
    }
}