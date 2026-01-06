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

/**
 * ViewModel para Actividad 6: Sistema de Notificaciones
 * 
 * Demuestra:
 * - while(isActive) para bucles infinitos cancelables
 * - Job lifecycle management
 * - Corutinas de larga duración
 * - Verificación periódica de estado
 * - Limpieza automática en onCleared()
 */
class NotificationSystemViewModel : ViewModel() {
    
    private val _output = MutableLiveData<String>()
    val output: LiveData<String> = _output
    
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status
    
    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> = _isRunning
    
    private val outputBuilder = StringBuilder()
    private var notificationJob: Job? = null
    private var notificationCount = 0
    
    private val notifications = listOf(
        "📧 Tienes un nuevo mensaje",
        "🔔 Recordatorio: Revisar actualizaciones",
        "💬 Nuevo comentario en tu publicación",
        "⭐ Alguien dio like a tu foto",
        "📱 Actualización disponible",
        "🎉 ¡Felicidades! Nuevo logro desbloqueado",
        "👥 Nueva solicitud de amistad",
        "🛍️ Oferta especial disponible"
    )
    
    /**
     * Inicia el sistema de notificaciones
     * Envía mensajes periódicamente mientras el Job está activo
     */
    fun startNotifications() {
        // Cancelar job anterior si existe
        notificationJob?.cancel()
        
        outputBuilder.clear()
        notificationCount = 0
        _status.value = "Ejecutando..."
        _isRunning.value = true
        
        appendOutput("🔔 Sistema de notificaciones iniciado")
        appendOutput("ℹ️ Usando while(isActive) para bucle cancelable")
        appendOutput("")
        
        // Crear job con bucle infinito
        notificationJob = viewModelScope.launch {
            try {
                // while(isActive) verifica si la corutina está activa
                // Si se cancela, isActive se vuelve false y el bucle termina
                while (isActive) {
                    // Esperar antes de enviar notificación
                    delay(3000) // Cada 3 segundos
                    
                    // Verificar nuevamente isActive después del delay
                    if (isActive) {
                        sendNotification()
                    }
                }
            } catch (e: Exception) {
                appendOutput("")
                appendOutput("❌ Sistema de notificaciones detenido")
                _status.value = "Cancelado"
            } finally {
                _isRunning.value = false
            }
        }
    }
    
    /**
     * Detiene el sistema de notificaciones
     */
    fun stopNotifications() {
        appendOutput("")
        appendOutput("🛑 Deteniendo sistema...")
        
        notificationJob?.cancel()
        notificationJob = null
    }
    
    /**
     * Envía una notificación simulada
     */
    private fun sendNotification() {
        notificationCount++
        
        // Seleccionar notificación aleatoria
        val notification = notifications.random()
        
        appendOutput("[$notificationCount] $notification")
        
        // Límite para la demo (20 notificaciones)
        if (notificationCount >= 20) {
            appendOutput("")
            appendOutput("ℹ️ Límite de demo alcanzado (20 notificaciones)")
            appendOutput("   Sistema detenido automáticamente")
            stopNotifications()
        }
    }
    
    private fun appendOutput(text: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            .format(Date())
        outputBuilder.append("[$timestamp] $text\n")
        _output.value = outputBuilder.toString()
    }
    
    override fun onCleared() {
        super.onCleared()
        // Importante: Limpiar recursos cuando el ViewModel se destruye
        // viewModelScope cancela automáticamente todas las corutinas
        notificationJob?.cancel()
        appendOutput("")
        appendOutput("🧹 ViewModel destruido, corutinas canceladas")
    }
}
