package io.jcasas.weatherdagger2example.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jcasas.weatherdagger2example.domain.config.Configuration
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.interactors.GetConfiguration
import io.jcasas.weatherdagger2example.interactors.GetCurrentWeatherWithLocation
import io.jcasas.weatherdagger2example.interactors.GetOneWeekForecast
import io.jcasas.weatherdagger2example.model.Weather
import io.jcasas.weatherdagger2example.model.transformation.Transformers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val getCurrentWeatherWithLocation: GetCurrentWeatherWithLocation,
        private val getOneWeekForecast: GetOneWeekForecast,
        private val getConfiguration: GetConfiguration
) : ViewModel() {

    val currentWeatherLiveData: LiveData<Weather> = MutableLiveData()
    val forecastLiveData: LiveData<List<ForecastEntity>> = MutableLiveData()

    fun getWeatherAtCurrentLocation() = viewModelScope.launch(Dispatchers.IO) {
        val weather = Transformers.fromDomainWeather(getCurrentWeatherWithLocation())
        (currentWeatherLiveData as MutableLiveData).postValue(weather)
    }

    fun fetchOneWeekForecast() = viewModelScope.launch(Dispatchers.IO) {
        val oneWeekForecast: List<ForecastEntity> = getOneWeekForecast()
        (forecastLiveData as MutableLiveData).postValue(oneWeekForecast)
    }

    fun getConfig(): Configuration = getConfiguration()
}