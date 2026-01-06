package com.example.actividades_corrutinas_kotlin.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.actividades_corrutinas_kotlin.R
import com.example.actividades_corrutinas_kotlin.viewmodels.WeatherForecastViewModel
import kotlinx.coroutines.launch

class WeatherForecastActivity : AppCompatActivity() {
    
    private lateinit var viewModel: WeatherForecastViewModel
    private lateinit var tvOutput: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnStart: Button
    private lateinit var btnClear: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)
        
        viewModel = ViewModelProvider(this)[WeatherForecastViewModel::class.java]
        
        tvOutput = findViewById(R.id.tv_output)
        tvStatus = findViewById(R.id.tv_status)
        btnStart = findViewById(R.id.btn_start)
        btnClear = findViewById(R.id.btn_clear)
        
        viewModel.output.observe(this) { output ->
            tvOutput.text = output
        }
        
        viewModel.status.observe(this) { status ->
            tvStatus.text = status
        }
        
        btnStart.setOnClickListener {
            btnStart.isEnabled = false
            lifecycleScope.launch {
                viewModel.fetchWeatherConcurrently()
                btnStart.isEnabled = true
            }
        }
        
        btnClear.setOnClickListener {
            viewModel.clearOutput()
        }
    }
}
