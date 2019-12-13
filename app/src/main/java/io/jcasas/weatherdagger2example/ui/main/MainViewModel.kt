package io.jcasas.weatherdagger2example.ui.main

import androidx.lifecycle.ViewModel
import com.juancasasm.android.weatherexample.interactors.GetCurrentLocation
import com.juancasasm.android.weatherexample.interactors.GetCurrentWeatherWithLocation

class MainViewModel(
        private val getCurrentLocation: GetCurrentLocation,
        private val getCurrentWeatherWithLocation: GetCurrentWeatherWithLocation
) : ViewModel() {

}