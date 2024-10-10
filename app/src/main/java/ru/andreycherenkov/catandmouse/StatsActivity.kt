package ru.andreycherenkov.catandmouse

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StatsActivity : AppCompatActivity() {

    private lateinit var statsRecyclerView: RecyclerView
    private lateinit var statsAdapter: StatsAdapter

    private var totalClicksDb: TotalClicksDao = TotalClicksDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stats)

        statsRecyclerView = findViewById(R.id.statsRecyclerView)

        val statsList = totalClicksDb.getLast10Games()

        statsAdapter = StatsAdapter(statsList)
        statsRecyclerView.adapter = statsAdapter
        statsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}