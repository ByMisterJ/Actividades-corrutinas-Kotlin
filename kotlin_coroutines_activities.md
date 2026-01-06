# Documentación Técnica: Actividades de Corrutinas en Kotlin

## Descripción General

Este repositorio contiene 6 actividades prácticas progresivas que demuestran el uso de corrutinas en Kotlin para Android. Cada actividad está diseñada para ser ejecutable en Android Studio con una interfaz de usuario que muestra los resultados en pantalla.

## Tabla de Contenidos

1. [Actividad 1: Simulador de Tareas Secuenciales](#actividad-1-simulador-de-tareas-secuenciales)
2. [Actividad 2: Temporizador No Bloqueante](#actividad-2-temporizador-no-bloqueante)
3. [Actividad 3: Simulación de API Lenta](#actividad-3-simulación-de-api-lenta)
4. [Actividad 4: Pronóstico del Clima Concurrente](#actividad-4-pronóstico-del-clima-concurrente)
5. [Actividad 5: Descargador de Archivos](#actividad-5-descargador-de-archivos)
6. [Actividad 6: Sistema de Notificaciones](#actividad-6-sistema-de-notificaciones)

---

## Actividad 1: Simulador de Tareas Secuenciales

### Objetivo
Demostrar la ejecución secuencial de tareas asíncronas usando `suspend fun` y entender por qué NO usar `runBlocking` en el hilo principal de Android.

### Conceptos de Corrutinas Usados

- **`suspend fun`**: Funciones que pueden suspenderse y reanudarse sin bloquear el hilo
- **`delay()`**: Suspende la corutina por un tiempo determinado sin bloquear el hilo
- **`lifecycleScope`**: Scope vinculado al ciclo de vida de la Activity
- **`viewModelScope`**: Scope vinculado al ciclo de vida del ViewModel
- **Ejecución secuencial**: Una tarea espera a que termine la anterior

### Implementación Técnica

```kotlin
// En SequentialTasksViewModel.kt
suspend fun executeSequentialTasks() {
    val startTime = System.currentTimeMillis()
    
    // Tarea 1: Login (~2 segundos)
    val loginResult = performLogin()
    
    // Tarea 2: Cargar perfil (~1.5 segundos) - ESPERA a que login termine
    val profileResult = loadUserProfile(loginResult)
    
    // Tarea 3: Cargar preferencias (~1 segundo) - ESPERA a que perfil termine
    val preferencesResult = loadUserPreferences(loginResult)
    
    val totalTime = System.currentTimeMillis() - startTime
    // Tiempo total: ~4.5 segundos (suma de todas)
}

private suspend fun performLogin(): String {
    delay(2000) // Simula I/O sin bloquear el hilo
    return "user_token_12345"
}
```

### Por Qué NO Usar `runBlocking` en Android

- **`runBlocking`**: Bloquea el hilo actual hasta que todas las corutinas dentro terminen
- En el hilo principal (UI thread) esto causaría **ANR** (Application Not Responding)
- Solo es apropiado para:
  - Funciones `main()` en aplicaciones de consola
  - Tests unitarios
  - Casos excepcionales fuera del main thread

### Qué Observar en la UI

1. Presionar "Iniciar"
2. Ver logs con timestamps mostrando:
   - Inicio de login
   - Completado de login
   - Inicio de carga de perfil (DESPUÉS de login)
   - Completado de perfil
   - Inicio de preferencias (DESPUÉS de perfil)
   - Completado de preferencias
3. Tiempo total: ~4500ms
4. Estado: "Ejecutando…" → "Finalizado"

---

## Actividad 2: Temporizador No Bloqueante

### Objetivo
Implementar un temporizador que cuenta segundos sin bloquear la UI, demostrando el uso de `launch`, `Job` y cancelación básica.

### Conceptos de Corrutinas Usados

- **`launch`**: Lanza una corutina que no retorna resultado
- **`Job`**: Referencia a una corutina para control (cancelar, esperar)
- **`Job.cancel()`**: Cancela la ejecución de una corutina
- **Bucle con `while(true)`**: Loop infinito que puede ser cancelado
- **`CancellationException`**: Excepción lanzada al cancelar

### Implementación Técnica

```kotlin
// En TimerViewModel.kt
private var timerJob: Job? = null

fun startTimer() {
    // launch crea una nueva corutina sin bloquear
    timerJob = viewModelScope.launch {
        try {
            var seconds = 0
            while (true) {
                delay(1000) // Suspende 1 segundo SIN bloquear
                seconds++
                _timerValue.value = seconds
                
                if (seconds >= 30) break // Límite para demo
            }
        } catch (e: Exception) {
            // Al cancelar, llegamos aquí
            _status.value = "Cancelado"
        }
    }
}

fun cancelTimer() {
    timerJob?.cancel() // Cancela la corutina
}
```

### Flujo de Cancelación

1. Usuario presiona "Cancelar"
2. Se llama `Job.cancel()`
3. En la próxima suspensión (`delay`), se lanza `CancellationException`
4. El bloque `catch` maneja la excepción
5. Estado cambia a "Cancelado"

### Qué Observar en la UI

1. Presionar "Iniciar"
2. Ver contador aumentando cada segundo
3. Logs mostrando cada tick
4. UI permanece responsive (puedes interactuar)
5. Presionar "Cancelar" en cualquier momento
6. Ver estado cambiar a "Cancelado"
7. Botones se habilitan/deshabilitan correctamente

---

## Actividad 3: Simulación de API Lenta

### Objetivo
Demostrar el uso de `suspend fun` en una capa de repositorio, separando responsabilidades y manejando resultados.

### Conceptos de Corrutinas Usados

- **`suspend fun` en repositorio**: Función suspendible en capa de datos
- **Repository pattern**: Separación de lógica de negocio y acceso a datos
- **Manejo de errores**: Try-catch en corutinas
- **Simulación de I/O**: `delay()` para simular latencia de red

### Implementación Técnica

```kotlin
// Repository separado (FakeApiRepository)
class FakeApiRepository {
    suspend fun getUserData(): ApiResponse {
        // Simular latencia variable
        val latency = Random.nextLong(1500, 3000)
        delay(latency)
        
        // Simular error ocasional (20%)
        if (Random.nextFloat() < 0.2f) {
            throw ApiException("Error de red: Timeout")
        }
        
        return ApiResponse(
            id = Random.nextInt(1000, 9999),
            name = "Usuario de Prueba",
            email = "usuario@example.com",
            premium = Random.nextBoolean()
        )
    }
}

// En ViewModel
suspend fun fetchDataFromApi() {
    try {
        val result = repository.getUserData() // Llamada suspendible
        // Procesar resultado
    } catch (e: Exception) {
        // Manejar error
    }
}
```

### Estructura de Capas

```
Activity → ViewModel → Repository → Datos
         lifecycleScope  suspend fun  delay()
```

### Qué Observar en la UI

1. Presionar "Iniciar"
2. Ver estado "Ejecutando…"
3. Logs mostrando:
   - Inicio de llamada
   - Tiempo de espera (~1.5-3 segundos)
   - Datos recibidos O error (20% probabilidad)
4. Datos formateados mostrados en pantalla
5. Tiempo total de operación
6. A veces verás error simulado - es intencional

---

## Actividad 4: Pronóstico del Clima Concurrente

### Objetivo
Demostrar ejecución concurrente usando `async`/`await` y medir la diferencia de tiempo vs ejecución secuencial.

### Conceptos de Corrutinas Usados

- **`async`**: Inicia una corutina que retorna un resultado futuro
- **`Deferred<T>`**: "Promesa" de un valor futuro (como Promise en JS)
- **`await()`**: Espera y obtiene el resultado de un `Deferred`
- **`measureTimeMillis`**: Mide tiempo de ejecución
- **Concurrencia real**: Múltiples tareas ejecutándose en paralelo

### Implementación Técnica

```kotlin
// En WeatherForecastViewModel.kt
suspend fun fetchWeatherConcurrently() {
    val totalTime = measureTimeMillis {
        viewModelScope.launch {
            // Tres async se inician SIMULTÁNEAMENTE
            val temperatureDeferred = async {
                fetchTemperature() // ~2 segundos
            }
            
            val humidityDeferred = async {
                fetchHumidity() // ~1.5 segundos
            }
            
            val windDeferred = async {
                fetchWindSpeed() // ~1 segundo
            }
            
            // await() espera los resultados
            // Como se ejecutaron en paralelo, el tiempo total es
            // el del más lento (~2 segundos), NO la suma (~4.5 segundos)
            val temperature = temperatureDeferred.await()
            val humidity = humidityDeferred.await()
            val wind = windDeferred.await()
        }.join()
    }
    // totalTime ≈ 2000ms (no 4500ms)
}
```

### Comparación: Secuencial vs Concurrente

| Enfoque | Temperatura | Humedad | Viento | Total |
|---------|------------|---------|--------|-------|
| Secuencial | 2000ms | + 1500ms | + 1000ms | **~4500ms** |
| Concurrente | ↓ | ↓ | ↓ | **~2000ms** |

### Qué Observar en la UI

1. Presionar "Iniciar"
2. Ver 3 peticiones iniciadas casi simultáneamente
3. Logs muestran timestamps cercanos para los 3 inicios
4. Esperar ~2 segundos (no 4.5)
5. Ver resultados:
   - Temperatura: XX°C
   - Humedad: XX%
   - Viento: XX km/h
6. **Análisis mostrado**:
   - Tiempo secuencial esperado: ~4500ms
   - Tiempo concurrente real: ~2000ms
   - Ahorro: ~2500ms (¡56% más rápido!)

---

## Actividad 5: Descargador de Archivos

### Objetivo
Implementar descargas concurrentes de múltiples archivos con seguimiento de progreso y cancelación grupal.

### Conceptos de Corrutinas Usados

- **Múltiples `launch`**: Varias corutinas paralelas
- **`Job.join()`**: Esperar a que un Job termine
- **`isActive`**: Verificar si la corutina está activa
- **Cancelación grupal**: Cancelar múltiples Jobs
- **Estado compartido**: Actualizar progreso desde múltiples corutinas

### Implementación Técnica

```kotlin
// En FileDownloaderViewModel.kt
private val downloadJobs = mutableListOf<Job>()

fun startDownloads() {
    files.forEach { file ->
        val job = viewModelScope.launch {
            downloadFile(file) // Cada archivo en su propia corutina
        }
        downloadJobs.add(job)
    }
    
    // Esperar a que TODAS terminen
    viewModelScope.launch {
        downloadJobs.forEach { it.join() }
        // Cuando todas terminan, llegamos aquí
    }
}

fun cancelDownloads() {
    downloadJobs.forEach { it.cancel() } // Cancelar todas
}

private suspend fun downloadFile(file: FileInfo) {
    repeat(file.chunks) { chunk ->
        if (!isActive) throw Exception("Cancelled")
        delay(500)
        // Actualizar progreso...
    }
}
```

### Sincronización

```
Job1 → Archivo1 ──┐
Job2 → Archivo2 ──┤
Job3 → Archivo3 ──┼── join() → Todas completadas
Job4 → Archivo4 ──┤
Job5 → Archivo5 ──┘
```

### Qué Observar en la UI

1. Presionar "Iniciar"
2. Ver 5 descargas iniciándose
3. Progreso mostrado para cada archivo (0%, 25%, 50%, 75%, 100%)
4. Barra de progreso global aumentando
5. Logs intercalados de diferentes archivos (ejecución paralela)
6. **Probar cancelación**:
   - Presionar "Cancelar" durante descarga
   - Ver todas las descargas detenerse
   - Estado: "Cancelado"
7. Sin cancelación: Ver "Todas las descargas finalizadas!"

---

## Actividad 6: Sistema de Notificaciones

### Objetivo
Implementar un sistema que envía mensajes periódicos usando `while(isActive)` con control de ciclo de vida.

### Conceptos de Corrutinas Usados

- **`while(isActive)`**: Bucle que verifica si la corutina está activa
- **`isActive`**: Propiedad que indica si la corutina fue cancelada
- **Job lifecycle**: Inicio, ejecución, cancelación
- **Corutinas de larga duración**: Procesos que corren indefinidamente
- **`onCleared()`**: Limpieza automática cuando el ViewModel se destruye

### Implementación Técnica

```kotlin
// En NotificationSystemViewModel.kt
fun startNotifications() {
    notificationJob = viewModelScope.launch {
        try {
            // while(isActive) verifica automáticamente si fue cancelado
            while (isActive) {
                delay(3000) // Cada 3 segundos
                
                if (isActive) { // Verificar nuevamente después del delay
                    sendNotification()
                }
                
                if (notificationCount >= 20) break // Límite demo
            }
        } catch (e: Exception) {
            // Si se cancela, llegamos aquí
        }
    }
}

fun stopNotifications() {
    notificationJob?.cancel() // isActive se vuelve false
}
```

### Ciclo de Vida

```
startNotifications()
    ↓
while(isActive) ← true
    ↓
delay(3000)
    ↓
sendNotification()
    ↓
while(isActive) ← ¿Cancelado?
    ↓               ↓ No
delay(3000)    isActive = true → Continuar
    ↓               ↓ Sí
catch           isActive = false → Terminar
```

### Qué Observar en la UI

1. Presionar "Iniciar"
2. Ver notificaciones apareciendo cada 3 segundos:
   - "📧 Tienes un nuevo mensaje"
   - "🔔 Recordatorio: Revisar actualizaciones"
   - etc.
3. Contador de notificaciones incrementando
4. **Probar cancelación**:
   - Presionar "Cancelar" durante ejecución
   - Ver sistema detenerse inmediatamente
   - Próximo `delay` o `isActive` detecta cancelación
5. **Límite automático**: Después de 20 notificaciones, se detiene solo
6. Estado: "Ejecutando…" → "Cancelado"

---

## Conceptos Generales y Mejores Prácticas

### ¿Por Qué NO Usar `GlobalScope`?

```kotlin
// ❌ MAL - No vinculado al ciclo de vida
GlobalScope.launch {
    // Si la Activity se destruye, esto sigue ejecutándose
}

// ✅ BIEN - Vinculado al ciclo de vida
lifecycleScope.launch {
    // Se cancela automáticamente cuando la Activity se destruye
}

// ✅ MEJOR - Separación de responsabilidades
viewModelScope.launch {
    // Se cancela cuando el ViewModel se destruye
}
```

### Scopes Recomendados

| Scope | Uso | Cancelación Automática |
|-------|-----|------------------------|
| `lifecycleScope` | Activity/Fragment | Cuando se destruye |
| `viewModelScope` | ViewModel | Cuando se destruye |
| `GlobalScope` | ❌ Evitar | Manual (fácil leak) |

### Estructura de Proyecto

```
app/
├── activities/
│   ├── SequentialTasksActivity.kt
│   ├── TimerActivity.kt
│   ├── ApiSimulationActivity.kt
│   ├── WeatherForecastActivity.kt
│   ├── FileDownloaderActivity.kt
│   └── NotificationSystemActivity.kt
├── viewmodels/
│   ├── SequentialTasksViewModel.kt
│   ├── TimerViewModel.kt
│   ├── ApiSimulationViewModel.kt
│   ├── WeatherForecastViewModel.kt
│   ├── FileDownloaderViewModel.kt
│   └── NotificationSystemViewModel.kt
└── MainActivity.kt (menú principal)
```

### Dependencias Necesarias

```gradle
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
}
```

## Resumen de Conceptos por Actividad

| Actividad | Concepto Principal | Secundarios |
|-----------|-------------------|-------------|
| 1 | `suspend fun`, secuencial | `delay`, `lifecycleScope` |
| 2 | `launch`, `Job.cancel()` | Bucles, estado |
| 3 | Repository pattern | `suspend fun` en capas |
| 4 | `async`/`await`, concurrencia | `measureTimeMillis` |
| 5 | Múltiples `launch`, `join()` | `isActive`, cancelación |
| 6 | `while(isActive)`, lifecycle | Jobs de larga duración |

## Cómo Ejecutar

1. Abrir proyecto en Android Studio
2. Sync Gradle
3. Ejecutar en emulador o dispositivo
4. Navegar por el menú principal
5. Seleccionar cualquier actividad
6. Presionar "Iniciar" y observar logs/resultados
7. Probar cancelación cuando esté disponible

## Referencias

- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)
- [Android Coroutines Best Practices](https://developer.android.com/kotlin/coroutines/coroutines-best-practices)
- [ViewModel Lifecycle](https://developer.android.com/topic/libraries/architecture/viewmodel)

---

**Autor**: Práctica implementada para aprendizaje de corrutinas en Kotlin  
**Última actualización**: 2026-01-06
