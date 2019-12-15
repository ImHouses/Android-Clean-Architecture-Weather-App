/*
 * Copyright 2019, Juan Casas
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
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.jcasas.weatherdagger2example.R
import io.jcasas.weatherdagger2example.domain.Forecast
import io.jcasas.weatherdagger2example.domain.ForecastResponse
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.WeatherApp
import io.jcasas.weatherdagger2example.databinding.ActivityMainBinding
import io.jcasas.weatherdagger2example.model.Weather
import io.jcasas.weatherdagger2example.ui.main.adapter.ForecastAdapter
import io.jcasas.weatherdagger2example.util.ActivityUtils
import io.jcasas.weatherdagger2example.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: MainViewModel
    private lateinit var mBinding: ActivityMainBinding
    private val mForecastList: ArrayList<Forecast> = ArrayList()
    private val mForecastAdapter: ForecastAdapter = ForecastAdapter(mForecastList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        inject()
        bindUi()
        initialize()
    }

    private fun inject() {
        (application as WeatherApp).getUiInjector().inject(this)
        mViewModel = ViewModelProvider(this, mFactory)[MainViewModel::class.java]
    }

    private fun bindUi() {
        mainProgressBar.visibility = View.VISIBLE
        mainSwipeRefreshLayout.setOnRefreshListener { mViewModel.getWeatherAtCurrentLocation() }
        supportActionBar!!.title = ActivityUtils.getStringByRes(R.string.app_name, this)
        mViewModel.currentWeatherLiveData.observe(this, Observer { weather ->
            showWeather(weather)
            mBinding.isLoading = false
            mBinding.isRefreshing = false
        })
        rvForecast.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(ForecastAdapter.VerticalSpaceItemDecoration(200))
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            adapter = mForecastAdapter
        }
    }

    private fun initialize() {
        askLocationPermission()
        mViewModel.getWeatherAtCurrentLocation()
    }

    /**
     * // TODO Replace for Dexter Implementation.
     */
    private fun askLocationPermission() {
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
            mBinding.isLoading = true
            mViewModel.getWeatherAtCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when(requestCode) {
            Constants.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (!grantResults.isEmpty()
                        && grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                    mViewModel.getWeatherAtCurrentLocation()
                }  else {
                    Toast.makeText(this,
                            resources.getText(R.string.location_permission_error),
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showWeather(weather: Weather) {
        weatherIcon.setImageResource(ActivityUtils.getIconRes(weather.weatherId))
        val units = if (weather.units == Units.SI) "C" else "F"
        mBinding.apply {
            this.units = units
            this.weather = weather
        }
    }

    private fun showForecast(forecastResponse: ForecastResponse) {
        mForecastList.clear()
        mForecastList.addAll(forecastResponse.list)
        mForecastAdapter.notifyDataSetChanged()
    }

    private fun showErrorAlert(errorCode: Int) {
        if (errorCode == Constants.Errors.WEATHER_RETRIEVE_ERROR) {
            ActivityUtils.createStandardAlert(R.string.error_title_string,
                    R.string.main_weather_error,
                    this).show()
        }
    }
}
