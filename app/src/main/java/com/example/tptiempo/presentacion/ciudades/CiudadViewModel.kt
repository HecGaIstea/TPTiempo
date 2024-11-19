package com.example.tptiempo.presentacion.ciudades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.router.Router




class CiudadViewModel(
    private val repositorio: Repositorio,
    private val router: Router
) : ViewModel() {
    private val _estado = MutableStateFlow(CiudadEstado())
    val estado: StateFlow<CiudadEstado> = _estado

    fun enviarIntencion(intencion: CiudadIntencion) {
        when (intencion) {
            is CiudadIntencion.BuscarCiudad -> buscarCiudad(intencion.nombre)
        }
    }

    private fun buscarCiudad(nombre: String) {
        viewModelScope.launch {
            _estado.value = CiudadEstado(isLoading = true)

            try {
                val ciudades = repositorio.buscarCiudad(nombre)
                _estado.value = CiudadEstado(ciudades = ciudades)
            } catch (e: Exception) {
                _estado.value = CiudadEstado(error = e.message)
            }
        }
    }
}





