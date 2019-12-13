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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.jcasas.weatherdagger2example.R
import io.jcasas.weatherdagger2example.WeatherApp
import com.juancasasm.android.weatherexample.domain.Forecast
import com.juancasasm.android.weatherexample.domain.ForecastResponse
import com.juancasasm.android.weatherexample.domain.WeatherResponse
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

    lateinit var mSwipeRefresh: SwipeRefreshLayout

    lateinit var mForecastList: List<Forecast>

    lateinit var mRecyclerList: RecyclerView

    lateinit var mForecastAdapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectPresenter()
        bindUi()
        mPresenter.start()
    }

    private fun bindUi() {
        mTextTemperature = findViewById(R.id.textTemperature)
        mTextCityName = findViewById(R.id.textCityName)
        mTextWeatherDesc = findViewById(R.id.textWeatherDescription)
        mProgressBar = findViewById(R.id.mainProgressBar)
        mWeatherIcon = findViewById(R.id.weatherIcon)
        mTextWeatherTitle = findViewById(R.id.weatherTitle)
        mRecyclerList = findViewById(R.id.rvForecast)
        mProgressBar.visibility = View.VISIBLE
        mSwipeRefresh = findViewById(R.id.mainSwipeRefreshLayout)
        mSwipeRefresh.setOnRefreshListener { refresh() }
        supportActionBar!!.title = ActivityUtils.getStringByRes(R.string.app_name, this)
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
                mPresenter.loadForecast(location.latitude, location.longitude)
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
        mWeatherIcon.setImageResource(ActivityUtils.getIconRes(weatherResponse.weather[0].id))
        mTextTemperature.text = temperature
        mTextCityName.text = cityName
        mTextWeatherTitle.text = String.format(ActivityUtils.getStringByRes(R.string.main_weather_title, this), cityName)
        mTextWeatherDesc.text = weatherDescription
    }

    override fun showForecast(forecastResponse: ForecastResponse) {
        mForecastList = forecastResponse.list
        mForecastList.drop(0)
        mForecastAdapter = ForecastAdapter(mForecastList, this)
        mRecyclerList.layoutManager = LinearLayoutManager(this)
        mRecyclerList.addItemDecoration(ForecastAdapter.VerticalSpaceItemDecoration(20))
        mRecyclerList.isNestedScrollingEnabled = false
        mRecyclerList.setHasFixedSize(true)
        mRecyclerList.setAdapter(mForecastAdapter)
    }

    override fun showErrorAlert(errorCode: Int) {
        if (errorCode == Constants.Errors.WEATHER_RETRIEVE_ERROR) {
            ActivityUtils.createStandardAlert(R.string.error_title_string,
                    R.string.main_weather_error,
                    this).show()
        }
    }

    override fun injectPresenter() {
        DaggerMainActivityComponent.builder()
                .weatherAppComponent((application as WeatherApp).getAppComponent())
                .mainActivityModule(MainActivityModule(this))
                .build()
                .inject(this)
    }

    override fun hideRefreshing() {
        mSwipeRefresh.isRefreshing = false
    }

    private fun refresh() {
        setLocationListener()
    }

}
