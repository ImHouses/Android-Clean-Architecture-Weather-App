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

package io.jcasas.weatherdagger2example.ui.main

import io.jcasas.weatherdagger2example.data.source.DataManager
import io.jcasas.weatherdagger2example.data.source.model.ForecastResponse
import io.jcasas.weatherdagger2example.data.source.model.WeatherResponse
import io.jcasas.weatherdagger2example.util.Constants
import io.jcasas.weatherdagger2example.util.OnModelLoaded
import io.jcasas.weatherdagger2example.util.WeatherCallback

class MainActivityPresenter : MainActivityContract.Presenter{

    val view:MainActivityContract.View

    private val mDataManager:DataManager


    constructor(view:MainActivityContract.View, dataManager: DataManager) {
        this.view = view
        this.mDataManager = dataManager
        start()
    }

    override fun loadWeather(lat:Double?, lon:Double?) {
        if (lat == null || lon == null) {
            return
        }
        mDataManager.getWeather(lat, lon, object : WeatherCallback {
            override fun onWeatherRetrieve(response: WeatherResponse?) {
                if (response == null) {
                    view.showErrorAlert(Constants.Errors.WEATHER_RETRIEVE_ERROR)
                } else {
                    view.showWeather(response)
                    view.hideProgressBar()
                    view.hideRefreshing()
                }
            }
        })
    }

    override fun loadForecast(lat: Double?, lon: Double?) {
        if (lat == null || lon == null) {
            //Show an error in view.
            return
        }
        mDataManager.getForecast(lat, lon, object : OnModelLoaded<ForecastResponse> {
            override fun onModelLoaded(model: ForecastResponse?) {
                if (model == null) {
                    view.showErrorAlert(Constants.Errors.WEATHER_RETRIEVE_ERROR)
                } else {
                    view.showForecast(model)
                }
            }
        })
    }


    /* Asks for permissions. */
    override fun start() {
        view.askLocationPermission()
    }
}