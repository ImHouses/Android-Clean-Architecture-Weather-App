package io.jcasas.weatherdagger2example.data.config

import io.jcasas.weatherdagger2example.domain.config.Configuration
import io.jcasas.weatherdagger2example.domain.config.NetworkStatus

interface ConfigurationDataSource {

    fun getConfiguration(): Configuration

    fun saveLastUpdate(lastUpdate: Long)

    fun getNetworkStatus(): NetworkStatus
}