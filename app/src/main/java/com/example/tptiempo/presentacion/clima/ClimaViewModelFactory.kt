package com.example.tptiempo.presentacion.clima

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.router.Router

class ClimaViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val lat: Float,
    private val lon: Float,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
            return ClimaViewModel(repositorio,router,lat,lon,nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


