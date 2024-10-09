package ru.andreycherenkov.catandmouse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sizeSpinner: Spinner
    private lateinit var speedSpinner: Spinner
    private lateinit var mouseCountSeekBar: SeekBar
    private lateinit var mouseCountDisplay: TextView
    private lateinit var startButton: Button
    private lateinit var scoreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        val sizeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.size_options,
            android.R.layout.simple_spinner_item
        )
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizeSpinner.adapter = sizeAdapter

        val speedAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.speed_options,
            android.R.layout.simple_spinner_item
        )
        speedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        speedSpinner.adapter = speedAdapter

        sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedSize = parent.getItemAtPosition(position).toString()
                // Здесь можно добавить логику для обработки выбранного размера
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ничего не делаем
            }
        }

        // Обработчик выбора скорости
        speedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedSpeed = parent.getItemAtPosition(position).toString()
                // Здесь можно добавить логику для обработки выбранной скорости
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ничего не делаем
            }
        }

        // Обработчик изменения SeekBar
        mouseCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mouseCountDisplay.text = mouseCountSeekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Ничего не делаем
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Ничего не делаем
            }
        })

        // Обработчик нажатия кнопки "Начать игру"
        startButton.setOnClickListener {
//            val intent = Intent(this, GameActivity::class.java).apply {
//                putExtra("size", sizeSeekBar.progress + 10)
//                putExtra("speed", speedSeekBar.progress + 1000)
//                putExtra("quantity", quantitySeekBar.progress + 1)
//            }
//            startActivity(intent)
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("count", mouseCountSeekBar.progress)
            }
            startActivity(intent)
        }

        scoreButton.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initViews() {
        sizeSpinner = findViewById(R.id.sizeSpinner)
        speedSpinner = findViewById(R.id.speedSpinner)
        mouseCountSeekBar = findViewById(R.id.mouseCountSeekBar)
        mouseCountDisplay = findViewById(R.id.mouseCountDisplay)
        startButton = findViewById(R.id.startButton)
        scoreButton = findViewById(R.id.scoreButton)
    }
}
