package com.example.actividades_corrutinas_kotlin.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.actividades_corrutinas_kotlin.R
import com.example.actividades_corrutinas_kotlin.viewmodels.NotificationSystemViewModel

class NotificationSystemActivity : AppCompatActivity() {
    
    private lateinit var viewModel: NotificationSystemViewModel
    private lateinit var tvOutput: TextView
    private lateinit var tvStatus: TextView
    private lateinit var btnStart: Button
    private lateinit var btnCancel: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_system)
        
        viewModel = ViewModelProvider(this)[NotificationSystemViewModel::class.java]
        
        tvOutput = findViewById(R.id.tv_output)
        tvStatus = findViewById(R.id.tv_status)
        btnStart = findViewById(R.id.btn_start)
        btnCancel = findViewById(R.id.btn_cancel)
        
        viewModel.output.observe(this) { output ->
            tvOutput.text = output
        }
        
        viewModel.status.observe(this) { status ->
            tvStatus.text = status
        }
        
        viewModel.isRunning.observe(this) { isRunning ->
            btnStart.isEnabled = !isRunning
            btnCancel.isEnabled = isRunning
        }
        
        btnStart.setOnClickListener {
            viewModel.startNotifications()
        }
        
        btnCancel.setOnClickListener {
            viewModel.stopNotifications()
        }
    }
}
