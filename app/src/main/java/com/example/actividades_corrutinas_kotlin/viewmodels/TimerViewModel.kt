package com.example.actividades_corrutinas_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel para Actividad 2: Temporizador No Bloqueante
 * 
 * Demuestra:
 * - launch: Lanzar una corutina de forma asíncrona
 * - Job: Mantener referencia para cancelación
 * - Job.cancel(): Cancelar una corutina en ejecución
 * - delay(): Pausar sin bloquear el hilo
 * - Estado mutable con LiveData
 */
class TimerViewModel : ViewModel() {
    
    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output
    
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    
    private val _timerValue = MutableLiveData<Int>()
    val timerValue: LiveData<Int> = _timerValue
    
    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> = _isRunning
    
    private val outputBuilder = StringBuilder()
    private var timerJob: Job? = null
    
    /**
     * Inicia el temporizador usando launch
     * La corutina cuenta segundos sin bloquear el hilo principal
     */
    fun startTimer() {
        // Cancelar job anterior si existe
        timerJob?.cancel()
        
        outputBuilder.clear()
        _timerValue.value = 0
        _status.value = "Ejecutando..."
        _isRunning.value = true
        
        appendOutput("⏱️ Temporizador iniciado")
        appendOutput("ℹ️ Usando launch + delay (no bloquea UI)")
        appendOutput("")
        
        // launch crea una nueva corutina sin bloquear el hilo actual
        timerJob = viewModelScope.launch {
            try {
                var seconds = 0
                // Bucle infinito que se puede cancelar
                while (true) {
                    delay(1000) // Suspende la corutina 1 segundo SIN bloquear
                    seconds++
                    _timerValue.value = seconds
                    appendOutput("⏰ Segundo $seconds")
                    
                    // Límite para demo (30 segundos)
                    if (seconds >= 30) {
                        appendOutput("\n✓ Límite alcanzado (30 segundos)")
                        break
                    }
                }
                _status.value = "Finalizado"
                _isRunning.value = false
            } catch (e: Exception) {
                // Si se cancela, llegamos aquí
                appendOutput("\n❌ Temporizador cancelado")
                _status.value = "Cancelado"
                _isRunning.value = false
            }
        }
    }
    
    /**
     * Cancela el temporizador llamando a Job.cancel()
     * Esto lanza una CancellationException en la corutina
     */
    fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
        appendOutput("\n🛑 Cancelación solicitada...")
    }
    
    private fun appendOutput(text: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date())
        outputBuilder.append("[$timestamp] $text\n")
        _output.value = outputBuilder.toString()
    }
    
    override fun onCleared() {
        super.onCleared()
        // viewModelScope cancela automáticamente todas las corutinas
        // cuando el ViewModel se destruye
        timerJob?.cancel()
    }
}
