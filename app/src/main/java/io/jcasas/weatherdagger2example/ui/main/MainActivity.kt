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

import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.jcasas.weatherdagger2example.R
import io.jcasas.weatherdagger2example.data.source.model.WeatherResponse
import io.jcasas.weatherdagger2example.di.component.DaggerMainActivityComponent
import io.jcasas.weatherdagger2example.di.module.MainActivityModule
import io.jcasas.weatherdagger2example.util.ActivityUtils
import io.jcasas.weatherdagger2example.util.Constants
import io.jcasas.weatherdagger2example.util.Temp
import io.jcasas.weatherdagger2example.util.TempConverter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    @Inject
    lateinit var mPresenter:MainActivityContract.Presenter

    lateinit var mTextTemperature:TextView

    lateinit var mTextCityName:TextView

    lateinit var mTextWeatherDesc:TextView

    lateinit var mTextWeatherTitle:TextView

    lateinit var mWeatherIcon:ImageView

    lateinit var mProgressBar:ProgressBar

    lateinit var mSwipeRefresh:SwipeRefreshLayout

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
        mWeatherIcon = findViewById(R.id.weatherIcon)
        mTextWeatherTitle = findViewById(R.id.weatherTitle)
        mProgressBar.visibility = View.VISIBLE
        mSwipeRefresh = findViewById(R.id.mainSwipeRefreshLayout)
        mSwipeRefresh.setOnRefreshListener { refresh() }
        supportActionBar!!.title = ActivityUtils.getStringByRes(R.string.app_name, this)

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

    private fun setLocationListener() {
        var mFusedLocationClient:FusedLocationProviderClient? = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.lastLocation.addOnSuccessListener(this) { location: Location? ->
            if (location != null) {
                mPresenter.loadWeather(location.latitude, location.longitude)
                mFusedLocationClient = null
            }
        }
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
        mWeatherIcon.setImageResource(getIconRes(weatherResponse.weather[0].id))
        mTextTemperature.text = temperature
        mTextCityName.text = cityName
        mTextWeatherTitle.text = String.format(ActivityUtils.getStringByRes(R.string.main_weather_title, this), cityName)
        mTextWeatherDesc.text = weatherDescription
    }

    override fun showErrorAlert(errorCode: Int) {
        if (errorCode == Constants.Errors.WEATHER_RETRIEVE_ERROR) {
            ActivityUtils.createStandardAlert(R.string.error_title_string,
                    R.string.main_weather_error,
                    this).show()
        }
    }

    override fun setPresenter(presenter: MainActivityContract.Presenter) { }

    override fun hideRefreshing() {
        mSwipeRefresh.isRefreshing = false
    }

    private fun refresh() {
        setLocationListener()
    }

    private fun getIconRes(id:Int):Int = when(id) {
        in 200..232 -> R.mipmap.ic_storm
        in 300..321 -> R.mipmap.ic_rain
        in 500..504 -> R.mipmap.ic_light_rain
        511 -> R.mipmap.ic_snowy_rain
        in 520..531 -> R.mipmap.ic_rain
        in 600..622 -> R.mipmap.ic_snow
        in 701..781 -> R.mipmap.ic_fog
        800 -> R.mipmap.ic_sun
        801 -> R.mipmap.ic_few_clouds
        in 802..804 -> R.mipmap.ic_cloudy
        else -> 0
    }
}
