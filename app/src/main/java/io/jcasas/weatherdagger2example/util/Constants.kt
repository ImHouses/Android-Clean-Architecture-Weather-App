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

package io.jcasas.weatherdagger2example.util

/**
 * Created by jcasas on 8/10/17.
 */
object Constants {

    object Errors {

        const val WEATHER_RETRIEVE_ERROR = 1

    }

    object Keys {
        const val GLOBAL_PREFS_NAME: String = "global_prefs"
        const val UNITS_KEY: String = "units"
        const val LOCATION_LAST_UPDATE: String = "location_last_update"
        const val WEATHER_LAST_UPDATE: String = "weather_last_update"
    }

    object Values {
        const val UNITS_SI: String = "si"
        const val UNITS_IMPERIAL: String = "imperial"
    }

    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    const val API_KEY = "700ec11b01c5c28b59d8087a038c09c2"

    const val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    const val INTERNAL_CONFIG_PREFS: String = "internal_config_prefs"
}