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

package io.jcasas.weatherdagger2example

import android.app.Application
import io.jcasas.weatherdagger2example.di.AppComponent
import io.jcasas.weatherdagger2example.di.AppModule
import io.jcasas.weatherdagger2example.di.DaggerAppComponent

/**
 * Created by jcasas on 8/6/17.
 */
class WeatherApp : Application() {

    private lateinit var appComponent: AppComponent

    fun getAppComponent(): AppComponent {
        return appComponent
    }
    protected fun initDagger(app:WeatherApp):AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(app))
                .build()
    }
}