package com.example.tptiempo.presentacion.ciudades


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.router.Router

class CiudadesViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CiudadViewModel::class.java)) {
            return CiudadViewModel(repositorio, router) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
