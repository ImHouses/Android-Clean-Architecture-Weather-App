package io.jcasas.weatherdagger2example.framework

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.jcasas.weatherdagger2example.data.exceptions.LocationNullException
import io.jcasas.weatherdagger2example.data.location.LocationDataSource
import io.jcasas.weatherdagger2example.domain.Coordinates
import kotlinx.coroutines.tasks.await

class AppLocationDataSource(private val context: Context) : LocationDataSource {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override suspend fun getCurrent(): Coordinates {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = fusedLocationClient.lastLocation.await() ?: throw LocationNullException()
        return Coordinates(lon = location.longitude, lat = location.latitude)
    }
}