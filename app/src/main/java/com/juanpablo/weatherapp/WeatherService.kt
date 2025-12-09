package com.juanpablo.weatherapp

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Serviço responsável por buscar dados de previsão do tempo da API
 */
class WeatherService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    companion object {
        private const val TAG = "WeatherService"
        private const val BASE_URL = "http://agent-weathermap-env-env.eba-6pzgqekp.us-east-2.elasticbeanstalk.com/api/weather"
        private const val API_KEY = "AgentWeather2024_a8f3b9c1d7e2f5g6h4i9j0k1l2m3n4o5p6"
    }

    /**
     * Busca a previsão do tempo para uma cidade específica
     *
     * @param city Nome da cidade no formato "Cidade,Estado,País" (ex: "Passos,MG,BR")
     * @param days Número de dias de previsão
     * @param callback Callback com o resultado da operação
     */
    fun fetchWeather(
        city: String,
        days: Int,
        callback: (Result<WeatherResponse>) -> Unit
    ) {
        val url = buildUrl(city, days)
        Log.d(TAG, "Fetching weather from: $url")

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Network error: ${e.message}", e)
                callback(Result.failure(e))
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
                    Log.d(TAG, "Response code: ${response.code}")
                    Log.d(TAG, "Response body: $responseBody")

                    if (!response.isSuccessful) {
                        callback(Result.failure(IOException("HTTP ${response.code}: ${response.message}")))
                        return
                    }

                    if (responseBody.isNullOrEmpty()) {
                        callback(Result.failure(IOException("Empty response body")))
                        return
                    }

                    val weatherResponse = parseWeatherResponse(responseBody)
                    callback(Result.success(weatherResponse))

                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing response: ${e.message}", e)
                    callback(Result.failure(e))
                }
            }
        })
    }

    /**
     * Constrói a URL com os parâmetros necessários
     */
    private fun buildUrl(city: String, days: Int): String {
        return "$BASE_URL?city=$city&days=$days&APPID=$API_KEY"
    }

    /**
     * Faz o parse da resposta JSON para o modelo WeatherResponse
     */
    private fun parseWeatherResponse(jsonString: String): WeatherResponse {
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject

        val city = jsonObject.get("city")?.asString ?: ""
        val forecastArray = jsonObject.getAsJsonArray("forecast")

        val weatherList = mutableListOf<WeatherData>()

        forecastArray?.forEach { element ->
            val forecastObj = element.asJsonObject

            val weatherData = WeatherData(
                date = forecastObj.get("date")?.asString ?: "",
                minTemp = forecastObj.get("min_temp")?.asDouble ?: 0.0,
                maxTemp = forecastObj.get("max_temp")?.asDouble ?: 0.0,
                humidity = forecastObj.get("humidity")?.asDouble ?: 0.0,
                description = forecastObj.get("description")?.asString ?: "",
                icon = forecastObj.get("icon")?.asString ?: ""
            )

            weatherList.add(weatherData)
        }

        return WeatherResponse(city = city, forecast = weatherList)
    }
}
