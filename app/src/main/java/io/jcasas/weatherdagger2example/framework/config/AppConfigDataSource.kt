package io.jcasas.weatherdagger2example.framework

import android.content.SharedPreferences
import io.jcasas.weatherdagger2example.data.config.ConfigurationDataSource
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.config.Configuration
import io.jcasas.weatherdagger2example.util.Constants
import javax.inject.Inject

class AppConfigDataSource @Inject constructor(
        private val sharedPreferences: SharedPreferences
) : ConfigurationDataSource {

    override fun getConfiguration(): Configuration {
        val savedUnits = sharedPreferences.getString(
                Constants.Keys.UNITS_KEY,
                Constants.Values.UNITS_SI
        )
        return Configuration(
                if (savedUnits == Constants.Values.UNITS_SI) Units.SI else Units.IMPERIAL
        )
    }
}