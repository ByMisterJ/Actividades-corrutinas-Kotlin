# Visual Guide: Actividades de Corrutinas

Este documento proporciona una guía visual de lo que verás al ejecutar cada actividad.

## Pantalla Principal (Main Menu)

```
┌─────────────────────────────────────┐
│  Prácticas de Corrutinas en Kotlin │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Actividad 1: Tareas           │ │
│  │ Secuenciales                  │ │
│  │ Simula login → perfil →       │ │
│  │ preferencias                  │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Actividad 2: Temporizador     │ │
│  │ Cuenta segundos sin bloquear  │ │
│  │ UI                            │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ Actividad 3: API Simulada     │ │
│  │ Llamada API con delay         │ │
│  │ simulado                      │ │
│  └───────────────────────────────┘ │
│                                     │
│  [... 3 actividades más ...]      │
└─────────────────────────────────────┘
```

## Actividad 1: Tareas Secuenciales

**Lo que verás:**

```
Estado: Ejecutando...

[16:48:12.345] 🔐 Iniciando login...
[16:48:14.350] ✓ Login completado: user_token_12345

[16:48:14.351] 👤 Cargando perfil de usuario...
[16:48:15.855] ✓ Perfil cargado: Juan Pérez (juan@example.com)

[16:48:15.856] ⚙️ Cargando preferencias...
[16:48:16.860] ✓ Preferencias cargadas: Tema oscuro, Notificaciones: ON

[16:48:16.861] ⏱️ Tiempo total: 4516ms
[16:48:16.861] ℹ️ Las tareas se ejecutaron SECUENCIALMENTE
                 (una después de otra)

Estado: Finalizado
```

**Puntos clave:**
- Cada tarea espera a la anterior
- Tiempo total = suma de todos los delays
- Los timestamps muestran orden secuencial

---

## Actividad 2: Temporizador No Bloqueante

**Lo que verás:**

```
┌─────────────────────────────────────┐
│      23 segundos                    │  ← Contador grande
└─────────────────────────────────────┘

Estado: Ejecutando...

[16:50:00] ⏱️ Temporizador iniciado
[16:50:00] ℹ️ Usando launch + delay (no bloquea UI)

[16:50:01] ⏰ Segundo 1
[16:50:02] ⏰ Segundo 2
[16:50:03] ⏰ Segundo 3
...
[16:50:23] ⏰ Segundo 23

[Botón Cancelar activo - presiona en cualquier momento]

→ Al cancelar:
[16:50:24] 🛑 Cancelación solicitada...
[16:50:24] ❌ Temporizador cancelado

Estado: Cancelado
```

**Puntos clave:**
- UI permanece responsive durante la cuenta
- Puedes cancelar en cualquier momento
- El Job se cancela inmediatamente

---

## Actividad 3: Simulación de API Lenta

**Lo que verás:**

```
Estado: Ejecutando...

[16:52:30.100] 🌐 Iniciando llamada a API...
[16:52:30.101] ℹ️ Usando suspend fun en Repository

[16:52:30.102] 📡 Conectando al servidor...

[... delay de 1.5-3 segundos ...]

[16:52:32.450] ✓ Respuesta recibida en 2348ms

[16:52:32.451] 📦 Datos recibidos:
                  ID: 5847
                  Nombre: Usuario de Prueba
                  Email: usuario@example.com
                  Creado: Mon Jan 06 16:52:32 UTC 2026
                  Premium: Sí

[16:52:32.452] ℹ️ La función suspend permite escribir
                  código asíncrono de forma secuencial

Estado: Finalizado
```

**Caso de error (20% probabilidad):**

```
[16:52:30.100] 🌐 Iniciando llamada a API...
[16:52:32.450] ❌ Error después de 2350ms
               Error: Error de red: Timeout

Estado: Error
```

---

## Actividad 4: Pronóstico del Clima Concurrente

**Lo que verás:**

```
Estado: Ejecutando...

[16:54:10.100] 🌤️ Obteniendo pronóstico del clima...
[16:54:10.101] ℹ️ Usando async/await para concurrencia

[16:54:10.105] 🚀 Iniciando 3 peticiones concurrentes...

[16:54:10.110] 🌡️ Solicitando temperatura...
[16:54:10.111] 💧 Solicitando humedad...
[16:54:10.112] 💨 Solicitando viento...

[16:54:10.115] ⏳ Esperando resultados...

[... delay ~2 segundos (el más largo) ...]

[16:54:12.120] ✓ Temperatura: 28°C
[16:54:12.121] ✓ Humedad: 65%
[16:54:12.122] ✓ Viento: 18 km/h

[16:54:12.125] ⏱️ Tiempo total: 2015ms

[16:54:12.126] 📊 ANÁLISIS:
                  • Tiempo secuencial esperado: ~4500ms
                    (2000ms + 1500ms + 1000ms)
                  • Tiempo concurrente real: 2015ms
                  • Ahorro: ~2485ms

[16:54:12.127] ✨ Las tareas se ejecutaron EN PARALELO
                  gracias a async/await!

Estado: Finalizado
```

**Puntos clave:**
- 3 peticiones inician casi simultáneamente
- Tiempo total ≈ tiempo del más lento (NO suma)
- Ahorro de ~55% de tiempo

---

## Actividad 5: Descargador de Archivos

**Lo que verás:**

```
[Barra de progreso: ▓▓▓▓▓▓▓░░░ 60%]

Estado: Ejecutando...

[16:56:00] 📥 Iniciando descargas concurrentes...
[16:56:00] ℹ️ 5 archivos, cada uno con su launch

[16:56:00] ⬇️ Descargando document.pdf (8 chunks)...
[16:56:00] ⬇️ Descargando image.jpg (5 chunks)...
[16:56:00] ⬇️ Descargando video.mp4 (12 chunks)...
[16:56:00] ⬇️ Descargando audio.mp3 (6 chunks)...
[16:56:00] ⬇️ Descargando archive.zip (10 chunks)...

[16:56:01]    document.pdf: 25%
[16:56:01]    image.jpg: 20%
[16:56:01]    video.mp4: 8%
[16:56:01]    audio.mp3: 16%
[16:56:02]    document.pdf: 50%
[16:56:02]    image.jpg: 40%
...

[16:56:03] ✓ image.jpg completado
[16:56:04] ✓ audio.mp3 completado
[16:56:05] ✓ document.pdf completado
[16:56:06] ✓ archive.zip completado
[16:56:07] ✓ video.mp4 completado

[16:56:07] 🎉 Todas las descargas finalizadas!

Estado: Finalizado
```

**Al cancelar a mitad de proceso:**

```
[16:56:03] 🛑 Cancelando todas las descargas...
[16:56:03] ❌ document.pdf cancelado
[16:56:03] ❌ video.mp4 cancelado
[16:56:03] ❌ archive.zip cancelado

[16:56:03] ⚠️ Descargas canceladas

Estado: Cancelado
```

---

## Actividad 6: Sistema de Notificaciones

**Lo que verás:**

```
Estado: Ejecutando...

[16:58:00] 🔔 Sistema de notificaciones iniciado
[16:58:00] ℹ️ Usando while(isActive) para bucle cancelable

[16:58:03] [1] 📧 Tienes un nuevo mensaje
[16:58:06] [2] 🔔 Recordatorio: Revisar actualizaciones
[16:58:09] [3] 💬 Nuevo comentario en tu publicación
[16:58:12] [4] ⭐ Alguien dio like a tu foto
[16:58:15] [5] 📱 Actualización disponible
[16:58:18] [6] 🎉 ¡Felicidades! Nuevo logro desbloqueado
...

[Cada 3 segundos aparece una nueva notificación]

→ Al cancelar:
[16:58:24] 🛑 Deteniendo sistema...
[16:58:24] ❌ Sistema de notificaciones detenido

Estado: Cancelado
```

**Límite automático:**

```
...
[16:59:00] [20] 🛍️ Oferta especial disponible

[16:59:00] ℹ️ Límite de demo alcanzado (20 notificaciones)
           Sistema detenido automáticamente

Estado: Finalizado
```

---

## Elementos Comunes en Todas las Actividades

### Botones
- **[Iniciar]**: Inicia la corutina
- **[Cancelar]**: Detiene la corutina (actividades 2, 5, 6)
- **[Limpiar]**: Limpia el log de salida

### Indicadores de Estado
- **"Ejecutando…"**: Corutina en progreso
- **"Finalizado"**: Completado exitosamente
- **"Cancelado"**: Detenido por usuario
- **"Error"**: Ocurrió un error (Activity 3)

### Log de Salida
- Formato: `[HH:mm:ss.SSS] mensaje`
- Scroll automático
- Fuente monoespaciada
- Fondo gris claro

### Características de UI
- Material Design cards
- Responsive (no se congela)
- Back button funciona (vuelve al menú)
- Rotación de pantalla mantiene estado (gracias a ViewModel)

---

## Cómo Probar Cada Actividad

### Actividad 1
1. ✓ Presiona "Iniciar"
2. ✓ Observa timestamps - deben ser secuenciales
3. ✓ Verifica que tiempo total ≈ 4.5 segundos

### Actividad 2
1. ✓ Presiona "Iniciar"
2. ✓ Observa contador aumentando
3. ✓ UI debe permanecer responsive
4. ✓ Presiona "Cancelar" - debe detenerse inmediatamente

### Actividad 3
1. ✓ Presiona "Iniciar" varias veces
2. ✓ A veces verás éxito, a veces error (20%)
3. ✓ Tiempo de respuesta variable (1.5-3 seg)

### Actividad 4
1. ✓ Presiona "Iniciar"
2. ✓ Observa 3 peticiones iniciando juntas
3. ✓ Tiempo total debe ser ~2 segundos (no 4.5)
4. ✓ Verifica cálculo de ahorro

### Actividad 5
1. ✓ Presiona "Iniciar"
2. ✓ Observa progreso de múltiples archivos
3. ✓ Barra de progreso debe actualizarse
4. ✓ Presiona "Cancelar" a mitad - todos se detienen

### Actividad 6
1. ✓ Presiona "Iniciar"
2. ✓ Notificaciones cada 3 segundos
3. ✓ Presiona "Cancelar" - se detiene inmediatamente
4. ✓ O espera 20 notificaciones - se detiene solo

---

## Troubleshooting

### "La app se congela"
❌ Problema: Probablemente usaste `runBlocking` en UI thread
✅ Solución: Usa `lifecycleScope.launch` o `viewModelScope.launch`

### "Cancelar no funciona"
❌ Problema: No verificas `isActive` o no usas `delay`
✅ Solución: Agrega verificaciones de `isActive` en bucles

### "Memory leak"
❌ Problema: Usaste `GlobalScope` o no cancelaste Jobs
✅ Solución: Usa `viewModelScope` - cancela automáticamente

### "UI no se actualiza"
❌ Problema: Modificas variables en lugar de LiveData
✅ Solución: Usa `MutableLiveData` y observa en Activity

---

**Nota**: Todos los ejemplos de output son aproximados. Los timestamps, delays y mensajes aleatorios variarán en cada ejecución.
