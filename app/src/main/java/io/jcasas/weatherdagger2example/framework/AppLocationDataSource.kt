package io.jcasas.weatherdagger2example.framework

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.juancasasm.android.weatherexample.data.exceptions.LocationNullException
import com.juancasasm.android.weatherexample.data.location.LocationDataSource
import com.juancasasm.android.weatherexample.domain.Coordinates
import kotlinx.coroutines.tasks.await

class AppLocationDataSource(private val context: Context) : LocationDataSource {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override suspend fun getCurrent(): Coordinates {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = fusedLocationClient.lastLocation.await() ?: throw LocationNullException()
        return Coordinates(lon = location.longitude, lat = location.latitude)
    }
}