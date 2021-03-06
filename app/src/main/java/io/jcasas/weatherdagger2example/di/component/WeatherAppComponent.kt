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

package io.jcasas.weatherdagger2example.di.component

import androidx.lifecycle.ViewModelProvider
import dagger.Component
import io.jcasas.weatherdagger2example.di.module.AppModule
import io.jcasas.weatherdagger2example.di.module.DataModule
import io.jcasas.weatherdagger2example.di.module.FrameworkDataModule
import io.jcasas.weatherdagger2example.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [FrameworkDataModule::class, DataModule::class, AppModule::class, ViewModelModule::class])
interface WeatherAppComponent {

    /* Exposed dependencies. */
    fun viewModelFactory(): ViewModelProvider.Factory

}