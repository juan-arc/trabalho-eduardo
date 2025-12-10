package com.juanpablo.weatherapp

/**
 * Modelo de dados para a resposta da API de previsão do tempo
 */
data class WeatherResponse(
    val city: String,
    val forecast: List<WeatherData>
)
