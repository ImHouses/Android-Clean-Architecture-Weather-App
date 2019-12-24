package io.jcasas.weatherdagger2example.framework

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.jcasas.weatherdagger2example.data.exceptions.LocationNullException
import io.jcasas.weatherdagger2example.data.location.LocationDataSource
import io.jcasas.weatherdagger2example.domain.Coordinates
import kotlinx.coroutines.tasks.await
import org.joda.time.DateTime

class AppLocationDataSource(private val context: Context) : LocationDataSource {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastCoordinates: Coordinates
    private var lastUpdateTime: DateTime = DateTime.now()
    private val threshold = 3 * 600_000

    override suspend fun getCurrent(): Coordinates {
        val timeDifference = DateTime.now().millis - lastUpdateTime.millis
        if (timeDifference < threshold && this::lastCoordinates.isInitialized) {
            return lastCoordinates
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = fusedLocationClient.lastLocation.await() ?: throw LocationNullException()
        lastCoordinates = Coordinates(lon = location.longitude, lat = location.latitude)
        return lastCoordinates
    }
}