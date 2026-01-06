package com.example.actividades_corrutinas_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ViewModel para Actividad 1: Tareas Secuenciales
 * 
 * Demuestra el uso de:
 * - suspend functions
 * - delay() para simular operaciones I/O
 * - Ejecución secuencial (una tarea después de otra)
 * - viewModelScope para manejo automático del ciclo de vida
 * 
 * NOTA: runBlocking NO se usa aquí porque BLOQUEARÍA el hilo principal.
 * runBlocking es útil solo para tests o main() functions en apps de consola.
 * En Android, siempre usar lifecycleScope o viewModelScope.
 */
class SequentialTasksViewModel : ViewModel() {
    
    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output
    
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    
    private val outputBuilder = StringBuilder()
    
    /**
     * Ejecuta tareas de forma secuencial: Login → Perfil → Preferencias
     * Cada tarea espera a que la anterior termine antes de comenzar.
     */
    suspend fun executeSequentialTasks() {
        outputBuilder.clear()
        _status.value = "Ejecutando..."
        
        val startTime = System.currentTimeMillis()
        
        // Tarea 1: Login
        appendOutput("🔐 Iniciando login...")
        val loginResult = performLogin()
        appendOutput("✓ Login completado: $loginResult")
        
        // Tarea 2: Cargar perfil (solo después de login)
        appendOutput("\n👤 Cargando perfil de usuario...")
        val profileResult = loadUserProfile(loginResult)
        appendOutput("✓ Perfil cargado: $profileResult")
        
        // Tarea 3: Cargar preferencias (solo después de perfil)
        appendOutput("\n⚙️ Cargando preferencias...")
        val preferencesResult = loadUserPreferences(loginResult)
        appendOutput("✓ Preferencias cargadas: $preferencesResult")
        
        val totalTime = System.currentTimeMillis() - startTime
        appendOutput("\n\n⏱️ Tiempo total: ${totalTime}ms")
        appendOutput("ℹ️ Las tareas se ejecutaron SECUENCIALMENTE")
        appendOutput("   (una después de otra)")
        
        _status.value = "Finalizado"
    }
    
    /**
     * Simula un login que tarda ~2 segundos
     */
    private suspend fun performLogin(): String {
        delay(2000) // Simula llamada a API de autenticación
        return "user_token_12345"
    }
    
    /**
     * Simula carga de perfil que tarda ~1.5 segundos
     */
    private suspend fun loadUserProfile(token: String): String {
        delay(1500) // Simula llamada a API de perfil
        return "Juan Pérez (juan@example.com)"
    }
    
    /**
     * Simula carga de preferencias que tarda ~1 segundo
     */
    private suspend fun loadUserPreferences(token: String): String {
        delay(1000) // Simula llamada a API de preferencias
        return "Tema oscuro, Notificaciones: ON"
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
