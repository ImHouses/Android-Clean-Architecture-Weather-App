package io.jcasas.weatherdagger2example.framework.config

import android.content.SharedPreferences
import android.net.ConnectivityManager
import io.jcasas.weatherdagger2example.data.config.ConfigurationDataSource
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.domain.config.Configuration
import io.jcasas.weatherdagger2example.domain.config.NetworkStatus
import io.jcasas.weatherdagger2example.util.Constants
import javax.inject.Inject

class AppConfigDataSource @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val connectivityMgr: ConnectivityManager
) : ConfigurationDataSource {

    override fun getConfiguration(): Configuration {
        val savedUnits = sharedPreferences.getString(
                Constants.Keys.UNITS_KEY,
                Constants.Values.UNITS_SI
        )
        return Configuration(
                if (savedUnits == Constants.Values.UNITS_SI) Units.SI else Units.IMPERIAL,
                sharedPreferences.getLong(Constants.Keys.WEATHER_LAST_UPDATE, System.currentTimeMillis())
        )
    }

    // TODO: Replace for a not deprecated implementation.
    override fun getNetworkStatus(): NetworkStatus {
        val activeNetworkInfo = connectivityMgr.activeNetworkInfo
        return if (activeNetworkInfo == null) {
            NetworkStatus.NOT_CONNECTED
        } else {
            val isConnected = activeNetworkInfo.isConnected
            if (isConnected) NetworkStatus.CONNECTED else NetworkStatus.NOT_CONNECTED
        }
    }
}