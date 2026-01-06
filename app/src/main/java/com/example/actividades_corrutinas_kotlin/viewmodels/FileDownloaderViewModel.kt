package com.example.actividades_corrutinas_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import kotlin.repeat

/**
 * ViewModel para Actividad 5: Descargador de Archivos
 * 
 * Demuestra:
 * - Múltiples launch ejecutándose en paralelo
 * - Job.join() para esperar finalización
 * - Cancelación de múltiples corutinas
 * - Progreso incremental
 * - Manejo de estado compartido
 */
class FileDownloaderViewModel : ViewModel() {
    
    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output
    
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    
    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress
    
    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> = _isRunning
    
    private val outputBuilder = StringBuilder()
    private val downloadJobs = mutableListOf<Job>()
    
    private val files = listOf(
        FileInfo("document.pdf", Random.nextInt(5, 10)),
        FileInfo("image.jpg", Random.nextInt(3, 7)),
        FileInfo("video.mp4", Random.nextInt(10, 15)),
        FileInfo("audio.mp3", Random.nextInt(4, 8)),
        FileInfo("archive.zip", Random.nextInt(8, 12))
    )
    
    /**
     * Inicia múltiples descargas simultáneas
     */
    fun startDownloads() {
        outputBuilder.clear()
        _progress.value = 0
        _status.value = "Ejecutando..."
        _isRunning.value = true
        downloadJobs.clear()
        
        appendOutput(" Iniciando descargas concurrentes...")
        appendOutput(" ${files.size} archivos, cada uno con su launch")
        appendOutput("")
        
        var completedFiles = 0
        val totalFiles = files.size
        
        // Crear un Job para cada archivo
        files.forEach { file ->
            val job = viewModelScope.launch {
                try {
                    downloadFile(file)
                    completedFiles++
                    updateProgress(completedFiles, totalFiles)
                    appendOutput(" ${file.name} completado")
                } catch (e: Exception) {
                    appendOutput(" ${file.name} cancelado")
                }
            }
            downloadJobs.add(job)
        }
        
        // Esperar a que todos terminen
        viewModelScope.launch {
            try {
                // join() espera a que cada Job termine
                downloadJobs.forEach { it.join() }
                
                if (completedFiles == totalFiles) {
                    appendOutput("")
                    appendOutput(" Todas las descargas finalizadas!")
                    _status.value = "Finalizado"
                } else {
                    appendOutput("")
                    appendOutput(" Descargas canceladas")
                    _status.value = "Cancelado"
                }
            } finally {
                _isRunning.value = false
                downloadJobs.clear()
            }
        }
    }
    
    /**
     * Cancela todas las descargas activas
     */
    fun cancelDownloads() {
        appendOutput("")
        appendOutput(" Cancelando todas las descargas...")
        
        // Cancelar todos los Jobs
        downloadJobs.forEach { it.cancel() }
    }
    
    /**
     * Simula descarga de un archivo con progreso
     */
    private suspend fun downloadFile(file: FileInfo) {
        viewModelScope.launch {
            appendOutput("⬇️ Descargando ${file.name} (${file.chunks} chunks)...")

            repeat(file.chunks) { chunk ->
                // Verificar si la corutina sigue activa
                while (isActive) { // This is a common pattern for long-running tasks
                    // Perform a piece of the download
                    println("Downloading chunk...")
                    delay(500) // Simulate work

                    // You can also check it with a simple 'if'
                    if (!isActive) {
                        println("Coroutine was cancelled, stopping download.")
                        break
                    }
                }

                val progress = ((chunk + 1) * 100 / file.chunks)
                appendOutput("   ${file.name}: ${progress}%")
            }
        }
    }
    
    private fun updateProgress(completed: Int, total: Int) {
        val percentage = (completed * 100) / total
        _progress.value = percentage
    }
    
    private fun appendOutput(text: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date())
        outputBuilder.append("[$timestamp] $text\n")
        _output.value = outputBuilder.toString()
    }
    
    override fun onCleared() {
        super.onCleared()
        downloadJobs.forEach { it.cancel() }
    }
}

/**
 * Información de archivo a descargar
 */
data class FileInfo(
    val name: String,
    val chunks: Int  // Número de "chunks" a descargar
)
