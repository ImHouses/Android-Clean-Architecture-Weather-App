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

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.jcasas.weatherdagger2example.R
import io.jcasas.weatherdagger2example.domain.forecast.ForecastEntity
import io.jcasas.weatherdagger2example.domain.forecast.ForecastResponse
import io.jcasas.weatherdagger2example.domain.Units
import io.jcasas.weatherdagger2example.WeatherApp
import io.jcasas.weatherdagger2example.databinding.ActivityMainBinding
import io.jcasas.weatherdagger2example.domain.config.Configuration
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
    private lateinit var mForecastList: ArrayList<ForecastEntity>
    private lateinit var mForecastAdapter: ForecastAdapter
    private lateinit var mConfiguration: Configuration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        inject()
        bindUi()
        askLocationPermission()
    }

    private fun inject() {
        (application as WeatherApp).getUiInjector().inject(this)
        mViewModel = ViewModelProvider(this, mFactory)[MainViewModel::class.java]
    }

    private fun bindUi() {
        mForecastList = ArrayList()
        mConfiguration = mViewModel.getConfig()
        mForecastAdapter = ForecastAdapter(mForecastList, mConfiguration.defaultUnits)
        mBinding.isLoading = true
        mainSwipeRefreshLayout.setOnRefreshListener { mViewModel.getWeatherAtCurrentLocation() }
        supportActionBar!!.title = ActivityUtils.getStringByRes(R.string.app_name, this)
        rvForecast.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            adapter = mForecastAdapter
        }
        mViewModel.currentWeatherLiveData.observe(this, Observer { weather ->
            showWeather(weather)
            mBinding.isLoading = false
            mBinding.isRefreshing = false
        })
        mViewModel.forecastLiveData.observe(this, Observer { forecast ->
            showForecast(forecast)
            mBinding.isLoading = false
            mBinding.isRefreshing = false
        })
    }

    private fun askLocationPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        mBinding.isLoading = true
                        mViewModel.getWeatherAtCurrentLocation()
                        mViewModel.fetchOneWeekForecast()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                    ) = Unit

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        AlertDialog.Builder(this@MainActivity)
                                .setTitle(R.string.main_location_permission_title)
                                .setMessage(R.string.main_location_permission)
                                .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                                    dialogInterface.dismiss()
                                    askLocationPermission()
                                }
                    }
                }).check()
    }

    private fun showWeather(weather: Weather) {
        weatherIcon.setImageResource(ActivityUtils.getIconRes(weather.weatherId))
        val units = if (weather.units == Units.SI) "C" else "F"
        mBinding.apply {
            this.units = units
            this.weather = weather
        }
    }

    private fun showForecast(forecastList: List<ForecastEntity>) {
        mForecastList.clear()
        mForecastList.addAll(forecastList)
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
