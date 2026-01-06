package com.example.actividades_corrutinas_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * ViewModel para Actividad 4: Pronóstico del Clima Concurrente
 * 
 * Demuestra:
 * - async: Ejecutar múltiples tareas en paralelo
 * - await: Esperar resultado de un Deferred
 * - Deferred<T>: Promesa de valor futuro
 * - measureTimeMillis: Medir tiempo de ejecución
 * - Beneficio de concurrencia vs secuencial
 */
class WeatherForecastViewModel : ViewModel() {
    
    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output
    
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    
    private val outputBuilder = StringBuilder()
    
    /**
     * Obtiene datos del clima de forma CONCURRENTE
     * Las tres llamadas se ejecutan en paralelo
     */
    suspend fun fetchWeatherConcurrently() {
        outputBuilder.clear()
        _status.value = "Ejecutando..."
        
        appendOutput("🌤️ Obteniendo pronóstico del clima...")
        appendOutput("ℹ️ Usando async/await para concurrencia")
        appendOutput("")
        
        // Medir tiempo total
        val totalTime = measureTimeMillis {
            viewModelScope.launch {
                // async inicia corutinas que se ejecutan EN PARALELO
                // Cada una retorna un Deferred<T> (promesa de resultado)
                appendOutput("🚀 Iniciando 3 peticiones concurrentes...")
                appendOutput("")
                
                val temperatureDeferred = async {
                    appendOutput("🌡️ Solicitando temperatura...")
                    fetchTemperature() // Tarda ~2 segundos
                }
                
                val humidityDeferred = async {
                    appendOutput("💧 Solicitando humedad...")
                    fetchHumidity() // Tarda ~1.5 segundos
                }
                
                val windDeferred = async {
                    appendOutput("💨 Solicitando viento...")
                    fetchWindSpeed() // Tarda ~1 segundo
                }
                
                appendOutput("")
                appendOutput("⏳ Esperando resultados...")
                appendOutput("")
                
                // await() espera el resultado de cada Deferred
                // Se bloquean aquí hasta que TODAS terminen
                val temperature = temperatureDeferred.await()
                appendOutput("✓ Temperatura: $temperature")
                
                val humidity = humidityDeferred.await()
                appendOutput("✓ Humedad: $humidity")
                
                val wind = windDeferred.await()
                appendOutput("✓ Viento: $wind")
            }.join()
        }
        
        appendOutput("")
        appendOutput("⏱️ Tiempo total: ${totalTime}ms")
        appendOutput("")
        appendOutput("📊 ANÁLISIS:")
        appendOutput("   • Tiempo secuencial esperado: ~4500ms")
        appendOutput("     (2000ms + 1500ms + 1000ms)")
        appendOutput("   • Tiempo concurrente real: ${totalTime}ms")
        appendOutput("   • Ahorro: ~${4500 - totalTime}ms")
        appendOutput("")
        appendOutput("✨ Las tareas se ejecutaron EN PARALELO")
        appendOutput("   gracias a async/await!")
        
        _status.value = "Finalizado"
    }
    
    /**
     * Simula obtener temperatura (tarda ~2 segundos)
     */
    private suspend fun fetchTemperature(): String {
        delay(2000)
        val temp = Random.nextInt(15, 35)
        return "${temp}°C"
    }
    
    /**
     * Simula obtener humedad (tarda ~1.5 segundos)
     */
    private suspend fun fetchHumidity(): String {
        delay(1500)
        val humidity = Random.nextInt(40, 90)
        return "${humidity}%"
    }
    
    /**
     * Simula obtener velocidad del viento (tarda ~1 segundo)
     */
    private suspend fun fetchWindSpeed(): String {
        delay(1000)
        val speed = Random.nextInt(5, 40)
        return "${speed} km/h"
    }
    
    private fun appendOutput(text: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
            .format(Date())
        outputBuilder.append("[$timestamp] $text\n")
        _output.value = outputBuilder.toString()
    }
    
    fun clearOutput() {
        outputBuilder.clear()
        _output.value = ""
        _status.value = ""
    }
}
