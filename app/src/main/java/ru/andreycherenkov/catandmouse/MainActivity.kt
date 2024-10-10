package ru.andreycherenkov.catandmouse

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

        mouseCountSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mouseCountDisplay.text = mouseCountSeekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        startButton.setOnClickListener {

            val context: Context = this
            val sizeOptions = context.resources.getStringArray(R.array.size_options)
            val speedOptions = context.resources.getStringArray(R.array.speed_options)
            val selectedSize = sizeSpinner.selectedItem.toString()
            val selectedSpeed = speedSpinner.selectedItem.toString()

            val sizeValue = when (selectedSize) {
                sizeOptions[0] -> 150
                sizeOptions[1] -> 300
                sizeOptions[2] -> 500
                else -> 300
            }

            val speedValue = when (selectedSpeed) {
                speedOptions[0] -> 2000
                speedOptions[1] -> 1500
                speedOptions[2] -> 1000
                else -> 1000
            }

            val intent = GameActivity.getIntent(
                this,
                GameActivity()::class.java,
                sizeValue,
                speedValue,
                mouseCountSeekBar.progress
            )
            startActivity(intent)
        }

        scoreButton.setOnClickListener {
            val intent = StatsActivity.getIntent(this, StatsActivity::class.java)
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
