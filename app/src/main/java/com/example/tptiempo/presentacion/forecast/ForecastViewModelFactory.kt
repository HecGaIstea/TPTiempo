package com.example.tptiempo.presentacion.forecast



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.router.Router

class ForecastViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val nombre: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            return ForecastViewModel(repositorio, router, nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
