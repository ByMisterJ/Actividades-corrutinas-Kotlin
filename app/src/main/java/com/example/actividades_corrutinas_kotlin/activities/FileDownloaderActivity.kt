package com.example.actividades_corrutinas_kotlin.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.actividades_corrutinas_kotlin.R
import com.example.actividades_corrutinas_kotlin.viewmodels.FileDownloaderViewModel


class FileDownloaderActivity : AppCompatActivity() {
    
    private lateinit var viewModel: FileDownloaderViewModel
    private lateinit var tvOutput: TextView
    private lateinit var tvStatus: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnStart: Button
    private lateinit var btnCancel: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_downloader)
        
        viewModel = ViewModelProvider(this)[FileDownloaderViewModel::class.java]
        
        tvOutput = findViewById(R.id.tv_output)
        tvStatus = findViewById(R.id.tv_status)
        progressBar = findViewById(R.id.progress_bar)
        btnStart = findViewById(R.id.btn_start)
        btnCancel = findViewById(R.id.btn_cancel)
        
        viewModel.output.observe(this) { output ->
            tvOutput.text = output
        }
        
        viewModel.status.observe(this) { status ->
            tvStatus.text = status
        }
        
        viewModel.progress.observe(this) { progress ->
            progressBar.progress = progress
        }
        
        viewModel.isRunning.observe(this) { isRunning ->
            btnStart.isEnabled = !isRunning
            btnCancel.isEnabled = isRunning
        }
        
        btnStart.setOnClickListener {
            viewModel.startDownloads()
        }
        
        btnCancel.setOnClickListener {
            viewModel.cancelDownloads()
        }
    }
}
