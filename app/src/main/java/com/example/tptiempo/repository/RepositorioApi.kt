package com.example.tptiempo.repository

import com.example.tptiempo.repository.modelos.Ciudad
import com.example.tptiempo.repository.modelos.Clima
import com.example.tptiempo.repository.modelos.ForecastDTO
import com.example.tptiempo.repository.modelos.ListForecast
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RepositorioApi : Repositorio {

    private val apiKey = "3897a2d3f43a99f8ff934a9f232e2427"

    private val cliente = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun buscarCiudad(ciudad: String): List<Ciudad> {
        val respuesta = cliente.get("https://api.openweathermap.org/geo/1.0/direct") {
            parameter("q", ciudad)
            parameter("limit", 100)
            parameter("appid", apiKey)
        }

        if (respuesta.status == HttpStatusCode.OK) {
            val ciudades = respuesta.body<List<Ciudad>>()
            return ciudades
        } else {
            throw Exception()
        }
    }

    override suspend fun traerClima(lat: Float, lon: Float): Clima {
        val respuesta = cliente.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("units", "metric")
            parameter("appid", apiKey)
        }
        if (respuesta.status == HttpStatusCode.OK) {
            val clima = respuesta.body<Clima>()
            return clima
        } else {
            throw Exception()
        }
    }

    override suspend fun traerPronostico(nombre: String): List<ListForecast> {
        val respuesta = cliente.get("https://api.openweathermap.org/data/2.5/forecast") {
            parameter("q", nombre)
            parameter("units", "metric")
            parameter("appid", apiKey)
        }
        if (respuesta.status == HttpStatusCode.OK) {
            val forecast = respuesta.body<ForecastDTO>()
            return forecast.list
        } else {
            throw Exception()
        }
    }
}
