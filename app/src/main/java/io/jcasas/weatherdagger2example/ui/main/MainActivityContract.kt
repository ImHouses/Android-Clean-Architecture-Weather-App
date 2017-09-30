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

import io.jcasas.weatherdagger2example.data.source.model.WeatherResponse
import io.jcasas.weatherdagger2example.ui.BasePresenter
import io.jcasas.weatherdagger2example.ui.BaseView

/**
 * Created by jcasas on 8/12/17.
 */
interface MainActivityContract {

    interface View : BaseView<Presenter> {

        fun askLocationPermission()

        fun showWeather(weatherResponse: WeatherResponse?)

        fun showErrorAlert(errorCode:Int)

        fun showProgressBar()

        fun hideProgressBar()

        fun hideRefreshing()

    }

    interface Presenter : BasePresenter {

        fun loadWeather(lat:Double?, lon:Double?)
    }

}