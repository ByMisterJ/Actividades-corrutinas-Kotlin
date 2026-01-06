package com.example.actividades_corrutinas_kotlin.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.actividades_corrutinas_kotlin.R
import com.example.actividades_corrutinas_kotlin.viewmodels.ApiSimulationViewModel
import kotlinx.coroutines.launch

class ApiSimulationActivity : AppCompatActivity() {
    
    private lateinit var viewModel: ApiSimulationViewModel
    private lateinit var tvOutput: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnStart: Button
    private lateinit var btnClear: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_simulation)
        
        viewModel = ViewModelProvider(this)[ApiSimulationViewModel::class.java]
        
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
                viewModel.fetchDataFromApi()
                btnStart.isEnabled = true
            }
        }
        
        btnClear.setOnClickListener {
            viewModel.clearOutput()
        }
    }
}
