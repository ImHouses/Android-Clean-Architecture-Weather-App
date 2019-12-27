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

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import io.jcasas.weatherdagger2example.data.location.LocationDataSource
import dagger.Module
import dagger.Provides
import io.jcasas.weatherdagger2example.di.ApplicationScope
import io.jcasas.weatherdagger2example.framework.AppLocationDataSource
import io.jcasas.weatherdagger2example.util.Constants
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = app

    @Provides
    @Singleton
    fun provideGlobalPreferences(): SharedPreferences = app.getSharedPreferences(
            Constants.Keys.GLOBAL_PREFS_NAME,
            Context.MODE_PRIVATE
    )

    @Provides
    @Singleton
    @Named("internal_config")
    fun provideInternalConfigPrefs(): SharedPreferences = app.getSharedPreferences(
        Constants.INTERNAL_CONFIG_PREFS,
            Context.MODE_PRIVATE
    )
}