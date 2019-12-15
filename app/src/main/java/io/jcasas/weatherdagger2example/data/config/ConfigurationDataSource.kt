package io.jcasas.weatherdagger2example.data.config

import io.jcasas.weatherdagger2example.domain.config.Configuration

interface ConfigurationDataSource {

    fun getConfiguration(): Configuration
}