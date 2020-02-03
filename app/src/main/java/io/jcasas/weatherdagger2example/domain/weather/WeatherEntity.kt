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

package io.jcasas.weatherdagger2example.domain.weather

import io.jcasas.weatherdagger2example.domain.Coordinates
import io.jcasas.weatherdagger2example.domain.Units
import org.joda.time.DateTime

data class WeatherEntity(
        val weatherId: Int,
        val status: String,
        val statusDescription: String,
        val temperature: Double,
        val minTemperature: Double,
        val maxTemperature: Double,
        val units: Units,
        val locationName: String,
        val coordinates: Coordinates,
        val lastUpdate: DateTime?,
        val humidity: Int?,
        val windSpeed: Double?
)