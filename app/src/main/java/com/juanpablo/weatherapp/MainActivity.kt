package com.juanpablo.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import androidx.recyclerview.widget.RecyclerView
import android.widget.ProgressBar

/**
 * Activity principal do aplicativo de previsão do tempo
 */
class MainActivity : AppCompatActivity() {

    private lateinit var cityEditText: TextInputEditText
    private lateinit var daysEditText: TextInputEditText
    private lateinit var searchFab: FloatingActionButton
    private lateinit var weatherRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var weatherAdapter: WeatherAdapter
    private val weatherService = WeatherService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupRecyclerView()
        setupListeners()
    }

    /**
     * Inicializa as views
     */
    private fun initializeViews() {
        cityEditText = findViewById(R.id.cityEditText)
        daysEditText = findViewById(R.id.daysEditText)
        searchFab = findViewById(R.id.searchFab)
        weatherRecyclerView = findViewById(R.id.weatherRecyclerView)
        progressBar = findViewById(R.id.progressBar)
    }

    /**
     * Configura o RecyclerView
     */
    private fun setupRecyclerView() {
        weatherAdapter = WeatherAdapter()
        weatherRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = weatherAdapter
        }
    }

    /**
     * Configura os listeners dos botões
     */
    private fun setupListeners() {
        searchFab.setOnClickListener {
            fetchWeatherData()
        }
    }

    /**
     * Busca os dados de previsão do tempo
     */
    private fun fetchWeatherData() {
        val city = cityEditText.text.toString().trim()
        val daysStr = daysEditText.text.toString().trim()

        // Validação
        if (city.isEmpty()) {
            showError(getString(R.string.error_empty_city))
            return
        }

        val days = daysStr.toIntOrNull() ?: 7

        // Mostra loading
        showLoading(true)

        // Faz a requisição
        weatherService.fetchWeather(city, days) { result ->
            runOnUiThread {
                showLoading(false)

                result.onSuccess { weatherResponse ->
                    if (weatherResponse.forecast.isEmpty()) {
                        showError("Nenhum dado de previsão encontrado")
                    } else {
                        weatherAdapter.updateWeatherList(weatherResponse.forecast)
                        Toast.makeText(
                            this,
                            "Previsão carregada: ${weatherResponse.city}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.onFailure { exception ->
                    handleError(exception)
                }
            }
        }
    }

    /**
     * Mostra ou esconde o indicador de loading
     */
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        searchFab.isEnabled = !show
    }

    /**
     * Trata erros da requisição
     */
    private fun handleError(exception: Throwable) {
        val errorMessage = when {
            exception.message?.contains("Unable to resolve host") == true ||
            exception.message?.contains("Network") == true -> {
                getString(R.string.error_network)
            }
            else -> {
                "${getString(R.string.error_api)}: ${exception.message}"
            }
        }
        showError(errorMessage)
    }

    /**
     * Exibe mensagem de erro
     */
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
