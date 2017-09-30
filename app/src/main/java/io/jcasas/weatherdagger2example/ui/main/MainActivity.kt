/*
 * Copyright 2017, Juan Casas
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

package io.jcasas.weatherdagger2example.ui.main

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import io.jcasas.weatherdagger2example.R
import io.jcasas.weatherdagger2example.data.source.model.WeatherResponse
import io.jcasas.weatherdagger2example.di.component.DaggerMainActivityComponent
import io.jcasas.weatherdagger2example.di.module.MainActivityModule
import io.jcasas.weatherdagger2example.util.ActivityUtils
import io.jcasas.weatherdagger2example.util.Constants
import io.jcasas.weatherdagger2example.util.Temp
import io.jcasas.weatherdagger2example.util.TempConverter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityContract.View, LocationListener {

    @Inject
    lateinit var mPresenter:MainActivityContract.Presenter

    lateinit var mTextTemperature:TextView

    lateinit var mTextCityName:TextView

    lateinit var mTextWeatherDesc:TextView

    lateinit var mProgressBar:ProgressBar

    lateinit var mSwipeRefresh:SwipeRefreshLayout

    lateinit var mLocationManager:LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainActivityComponent.builder()
                .mainActivityModule(MainActivityModule(this))
                .build()
                .inject(this)
        mTextTemperature = findViewById(R.id.textTemperature)
        mTextCityName = findViewById(R.id.textCityName)
        mTextWeatherDesc = findViewById(R.id.textWeatherDescription)
        mProgressBar = findViewById(R.id.mainProgressBar)
        mProgressBar.visibility = View.VISIBLE
        mSwipeRefresh = findViewById(R.id.mainSwipeRefreshLayout)
        mSwipeRefresh.setOnRefreshListener { refresh() }
        mPresenter.start()
    }

    override fun showProgressBar() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        mProgressBar.visibility = View.INVISIBLE
    }

    override fun askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {
            setLocationListener()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (!grantResults.isEmpty()
                        && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                    setLocationListener()
                }  else {
                    Toast.makeText(this,
                            resources.getText(R.string.location_permission_error),
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun setLocationListener() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100F, this)
    }

    override fun showWeather(weatherResponse: WeatherResponse?) {
        if (weatherResponse == null) {
            return
        }
        val temperature:String = (TempConverter.convert(weatherResponse.main.temp,
                Temp.KELVIN,
                Temp.CELSIUS).toInt()).toString() + " °C"
        val cityName:String = weatherResponse.name
        val weatherDescription = weatherResponse.weather[0].main
        val title:String = String.format("%s %s",
                resources.getText(R.string.main_toolbar_title),
                cityName)
        mTextTemperature.text = temperature
        mTextCityName.text = cityName
        supportActionBar!!.title = title
        mTextWeatherDesc.text = weatherDescription
    }

    override fun showErrorAlert(errorCode: Int) {
        if (errorCode == Constants.Errors.WEATHER_RETRIEVE_ERROR) {
            ActivityUtils.createStandardAlert(R.string.error_title_string,
                    R.string.main_weather_error,
                    this).show()
        }
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) {

    }

    override fun onLocationChanged(p0: Location?) {
        mPresenter.loadWeather(p0!!.latitude, p0!!.longitude)
        mLocationManager.removeUpdates(this)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) { }

    override fun onProviderEnabled(p0: String?) { }

    override fun onProviderDisabled(p0: String?) { }

    override fun hideRefreshing() {
        mSwipeRefresh.isRefreshing = false
    }

    private fun refresh() {
        setLocationListener()
    }
}
