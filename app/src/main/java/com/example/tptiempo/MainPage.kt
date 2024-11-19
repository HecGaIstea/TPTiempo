package com.example.tptiempo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.tptiempo.presentacion.ciudades.CiudadView
import com.example.tptiempo.presentacion.clima.ClimaView
import com.example.tptiempo.presentacion.forecast.ForecastView
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import com.example.tptiempo.presentacion.clima.ClimaViewModelFactory
import com.example.tptiempo.repository.RepositorioApi


class MainPage : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val repositorio = RepositorioApi()

        setContent {
            var lat by remember { mutableStateOf(0.0) }
            var lon by remember { mutableStateOf(0.0) }
            var selectedCity by remember { mutableStateOf<String?>(null) }

            val scope = rememberCoroutineScope()

            LaunchedEffect(Unit) {
                if (ActivityCompat.checkSelfPermission(this@MainPage, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    getLastLocation { location ->
                        lat = location.latitude
                        lon = location.longitude
                    }
                }
            }

            Column {
                CiudadView(onCitySelected = { cityName ->
                    selectedCity = cityName
                })

                if (selectedCity != null) {
                    // UI con la ciudad seleccionada
                    ClimaView(climaViewModel = viewModel(factory = ClimaViewModelFactory(lat, lon, repositorio)))
                    ForecastView(forecastViewModel = viewModel())
                } else {
                    Text("Selecciona una ciudad para ver el clima")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(onLocationObtained: (Location) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let { onLocationObtained(it) }
        }
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            getLastLocation { location ->
                lat = location.latitude
                lon = location.longitude
            }
        }
    }
}


