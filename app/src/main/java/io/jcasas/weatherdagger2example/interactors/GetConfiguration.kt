package io.jcasas.weatherdagger2example.interactors

import io.jcasas.weatherdagger2example.data.config.ConfigurationRepository
import io.jcasas.weatherdagger2example.domain.config.Configuration
import javax.inject.Inject

class GetConfiguration @Inject constructor(
        private val configurationRepository: ConfigurationRepository
) {
    operator fun invoke(): Configuration = configurationRepository.getConfiguration()
}