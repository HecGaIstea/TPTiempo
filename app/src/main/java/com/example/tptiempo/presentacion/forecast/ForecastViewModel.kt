package com.example.tptiempo.presentacion.forecast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tptiempo.repository.Repositorio
import com.example.tptiempo.repository.modelos.ListForecast
import com.example.tptiempo.router.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForecastViewModel(
    val respositorio: Repositorio,
    val router: Router,
    val nombre: String
) : ViewModel() {

    var uiState by mutableStateOf<ForecastEstado>(ForecastEstado.Vacio)

    fun ejecutar(intencion: ForecastIntencion){
        when(intencion){
            ForecastIntencion.actualizarClima -> traerPronostico()
        }
    }

    fun traerPronostico() {
        uiState = ForecastEstado.Cargando
        viewModelScope.launch {
            try{
                val forecast = respositorio.traerPronostico(nombre).filter {
                    //TODO agregar logica de filtrado
                    true
                }
                uiState = ForecastEstado.Exitoso(forecast)
            } catch (exception: Exception){
                uiState = ForecastEstado.Error(exception.localizedMessage ?: "error desconocido")
            }
        }
    }

}

class ForecastViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            return ForecastViewModel(repositorio,router,nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
