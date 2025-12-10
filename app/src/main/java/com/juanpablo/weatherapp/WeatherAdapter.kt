package com.juanpablo.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter para exibir a lista de previsões do tempo no RecyclerView
 */
class WeatherAdapter(
    private var weatherList: List<WeatherData> = emptyList()
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    /**
     * ViewHolder para os itens de previsão do tempo
     */
    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconTextView: TextView = itemView.findViewById(R.id.iconTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val temperatureTextView: TextView = itemView.findViewById(R.id.temperatureTextView)
        val humidityTextView: TextView = itemView.findViewById(R.id.humidityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]

        holder.iconTextView.text = weather.icon
        holder.dateTextView.text = weather.date
        holder.descriptionTextView.text = weather.description
        holder.temperatureTextView.text = holder.itemView.context.getString(
            R.string.temperature,
            weather.getMinTempFormatted(),
            weather.getMaxTempFormatted()
        )
        holder.humidityTextView.text = holder.itemView.context.getString(
            R.string.humidity,
            weather.getHumidityPercent()
        )
    }

    override fun getItemCount(): Int = weatherList.size

    /**
     * Atualiza a lista de previsões
     */
    fun updateWeatherList(newList: List<WeatherData>) {
        weatherList = newList
        notifyDataSetChanged()
    }
}
