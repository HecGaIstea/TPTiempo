package com.example.tptiempo.presentacion.clima

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tptiempo.repository.Repositorio

class ClimaViewModelFactory(
    private val lat: Double,
    private val lon: Double,
    private val repositorio: Repositorio
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClimaViewModel(repositorio, lat, lon) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


