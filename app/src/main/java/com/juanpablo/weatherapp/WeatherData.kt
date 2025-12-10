package com.juanpablo.weatherapp

/**
 * Modelo de dados para representar a previsão do tempo de um dia
 */
data class WeatherData(
    val date: String,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Double,
    val description: String,
    val icon: String
) {
    /**
     * Retorna a umidade como porcentagem
     */
    fun getHumidityPercent(): Int {
        return (humidity * 100).toInt()
    }

    /**
     * Formata a temperatura mínima
     */
    fun getMinTempFormatted(): String {
        return String.format("%.0f", minTemp)
    }

    /**
     * Formata a temperatura máxima
     */
    fun getMaxTempFormatted(): String {
        return String.format("%.0f", maxTemp)
    }
}
