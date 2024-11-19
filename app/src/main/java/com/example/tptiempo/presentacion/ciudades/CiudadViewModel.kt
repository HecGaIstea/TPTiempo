package com.example.tptiempo.presentacion.ciudades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.repository.modelos.Ciudad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CiudadViewModel(private val repositorio: Repositorio) : ViewModel() {

    private val _estado = MutableStateFlow(CiudadEstado())
    val estado: StateFlow<CiudadEstado> get() = _estado

    fun enviarIntencion(intencion: CiudadIntencion) {
        when (intencion) {
            is CiudadIntencion.BuscarCiudad -> buscarCiudad(intencion.ciudad)
        }
    }

    private fun buscarCiudad(ciudad: String) {
        viewModelScope.launch {
            _estado.value = CiudadEstado(isLoading = true)
            try {
                val ciudades = repositorio.buscarCiudad(ciudad)
                _estado.value = CiudadEstado(ciudades = ciudades)
            } catch (e: Exception) {
                _estado.value = CiudadEstado(error = "Error al buscar la ciudad")
            }
        }
    }
}
