package com.example.actividades_corrutinas_kotlin.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.actividades_corrutinas_kotlin.R
import com.example.actividades_corrutinas_kotlin.viewmodels.TimerViewModel


class TimerActivity : AppCompatActivity() {
    
    private lateinit var viewModel: TimerViewModel
    private lateinit var tvOutput: TextView
    private lateinit var tvStatus: TextView
    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnCancel: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        
        viewModel = ViewModelProvider(this)[TimerViewModel::class.java]
        
        tvOutput = findViewById(R.id.tv_output)
        tvStatus = findViewById(R.id.tv_status)
        tvTimer = findViewById(R.id.tv_timer)
        btnStart = findViewById(R.id.btn_start)
        btnCancel = findViewById(R.id.btn_cancel)
        
        // Observar cambios
        viewModel.output.observe(this) { output ->
            tvOutput.text = output
        }
        
        viewModel.status.observe(this) { status ->
            tvStatus.text = status
        }
        
        viewModel.timerValue.observe(this) { seconds ->
            tvTimer.text = "$seconds segundos"
        }
        
        viewModel.isRunning.observe(this) { isRunning ->
            btnStart.isEnabled = !isRunning
            btnCancel.isEnabled = isRunning
        }
        
        btnStart.setOnClickListener {
            viewModel.startTimer()
        }
        
        btnCancel.setOnClickListener {
            viewModel.cancelTimer()
        }
    }
}
