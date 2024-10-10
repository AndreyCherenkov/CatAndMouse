package ru.andreycherenkov.catandmouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class StatsAdapter(private val statsList: List<GameStats>) :
    RecyclerView.Adapter<StatsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val totalClicksTextView: TextView = itemView.findViewById(R.id.totalClicksTextView)
        val mouseClicksTextView: TextView = itemView.findViewById(R.id.mouseClicksTextView)
        val percentageTextView: TextView = itemView.findViewById(R.id.percentageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game_stats, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stats = statsList[position]
        holder.totalClicksTextView.text = "Всего кликов: ${stats.totalClicks}"
        holder.mouseClicksTextView.text = "Попаданий в цель: ${stats.mouseClicks}"

        val percentage = (stats.mouseClicks.toFloat() / stats.totalClicks.toFloat()) * 100

        val formattedPercentage = if (percentage.toDouble().isNaN()) {
            "0"
        } else {
            val decimalFormat = DecimalFormat("#.00")
            decimalFormat.format(percentage)
        }
        holder.percentageTextView.text = "Процент попаданий в цель: ${formattedPercentage}%"
    }

    override fun getItemCount(): Int {
        return statsList.size
    }
}
