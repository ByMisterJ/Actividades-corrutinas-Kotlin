package com.example.actividades_corrutinas_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * ViewModel para Actividad 3: Simulación de API Lenta
 * 
 * Demuestra:
 * - suspend fun en capa de repositorio
 * - Separación de lógica de negocio
 * - Manejo de casos success/error
 * - Simulación realista de llamadas de red
 */
class ApiSimulationViewModel : ViewModel() {
    
    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output
    
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    
    private val outputBuilder = StringBuilder()
    private val repository = FakeApiRepository()
    
    /**
     * Obtiene datos de la "API" simulada
     */
    suspend fun fetchDataFromApi() {
        outputBuilder.clear()
        _status.value = "Ejecutando..."
        
        appendOutput("🌐 Iniciando llamada a API...")
        appendOutput("ℹ️ Usando suspend fun en Repository")
        appendOutput("")
        
        val startTime = System.currentTimeMillis()
        
        try {
            // Llamada al repositorio (capa de datos)
            appendOutput("📡 Conectando al servidor...")
            val result = repository.getUserData()
            
            val elapsed = System.currentTimeMillis() - startTime
            
            appendOutput("✓ Respuesta recibida en ${elapsed}ms")
            appendOutput("")
            appendOutput("📦 Datos recibidos:")
            appendOutput(result.toString())
            
            _status.value = "Finalizado"
            
        } catch (e: Exception) {
            val elapsed = System.currentTimeMillis() - startTime
            appendOutput("❌ Error después de ${elapsed}ms")
            appendOutput("Error: ${e.message}")
            _status.value = "Error"
        }
        
        appendOutput("")
        appendOutput("ℹ️ La función suspend permite escribir")
        appendOutput("   código asíncrono de forma secuencial")
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

/**
 * Repositorio falso que simula una API
 * En una app real, esto usaría Retrofit, Ktor, etc.
 */
class FakeApiRepository {
    
    /**
     * suspend fun que simula una llamada HTTP
     * Puede ser llamada solo desde otra corutina o suspend fun
     */
    suspend fun getUserData(): ApiResponse {
        // Simular latencia de red variable
        val latency = Random.nextLong(1500, 3000)
        delay(latency)
        
        // Simular error ocasional (20% de probabilidad)
        if (Random.nextFloat() < 0.2f) {
            throw ApiException("Error de red: Timeout")
        }
        
        // Retornar datos simulados
        return ApiResponse(
            id = Random.nextInt(1000, 9999),
            name = "Usuario de Prueba",
            email = "usuario@example.com",
            createdAt = Date().toString(),
            premium = Random.nextBoolean()
        )
    }
}

/**
 * Modelo de datos de respuesta
 */
data class ApiResponse(
    val id: Int,
    val name: String,
    val email: String,
    val createdAt: String,
    val premium: Boolean
) {
    override fun toString(): String {
        return """
            |  ID: $id
            |  Nombre: $name
            |  Email: $email
            |  Creado: $createdAt
            |  Premium: ${if (premium) "Sí" else "No"}
        """.trimMargin()
    }
}

/**
 * Excepción personalizada para errores de API
 */
class ApiException(message: String) : Exception(message)
