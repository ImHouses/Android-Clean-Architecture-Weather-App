package io.jcasas.weatherdagger2example.data.config

import io.jcasas.weatherdagger2example.domain.config.Configuration
import javax.inject.Inject

class ConfigurationRepository
@Inject constructor(private val configurationDataSource: ConfigurationDataSource) {

    fun getConfiguration(): Configuration = configurationDataSource.getConfiguration()
}