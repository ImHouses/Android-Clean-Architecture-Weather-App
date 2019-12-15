package io.jcasas.weatherdagger2example.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jcasas.weatherdagger2example.interactors.GetCurrentWeatherWithLocation
import io.jcasas.weatherdagger2example.model.Weather
import io.jcasas.weatherdagger2example.model.transformation.Transformers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val getCurrentWeatherWithLocation: GetCurrentWeatherWithLocation
) : ViewModel() {

    val currentWeatherLiveData: LiveData<Weather> = MutableLiveData()

    fun getWeatherAtCurrentLocation() = viewModelScope.launch(Dispatchers.IO) {
        val weather = Transformers.fromDomainWeather(getCurrentWeatherWithLocation())
        (currentWeatherLiveData as MutableLiveData).postValue(weather)
    }
}