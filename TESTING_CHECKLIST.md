# Testing Checklist for Actividades-corrutinas-Kotlin

Este documento contiene una lista de verificación completa para probar todas las funcionalidades del proyecto.

## ✅ Pre-requisitos

- [ ] Android Studio instalado (Arctic Fox o superior)
- [ ] Dispositivo Android o emulador configurado
- [ ] SDK de Android descargado (API 24+)
- [ ] Conexión a internet para sincronización de Gradle

## 📋 Fase 1: Compilación y Setup

### 1.1 Clonar y Abrir
- [ ] Clonar repositorio exitosamente
- [ ] Abrir proyecto en Android Studio
- [ ] Gradle sync completa sin errores
- [ ] No aparecen errores de compilación en IDE

### 1.2 Verificar Dependencias
- [ ] kotlinx-coroutines-android (1.7.3) descargada
- [ ] kotlinx-coroutines-core (1.7.3) descargada
- [ ] lifecycle-runtime-ktx (2.8.7) descargada
- [ ] lifecycle-viewmodel-ktx (2.8.7) descargada

### 1.3 Build
- [ ] `./gradlew assembleDebug` completa exitosamente
- [ ] APK generado en `app/build/outputs/apk/debug/`
- [ ] Tamaño de APK razonable (~2-5 MB)

## 📱 Fase 2: Instalación y Lanzamiento

### 2.1 Instalación
- [ ] App se instala en dispositivo/emulador
- [ ] Icono aparece en launcher
- [ ] Nombre de app correcto: "Actividades-corrutinas-Kotlin"

### 2.2 Primer Lanzamiento
- [ ] App abre sin crash
- [ ] Pantalla principal muestra título
- [ ] Se ven 6 cards de actividades
- [ ] Cards son clickeables

## 🧪 Fase 3: Pruebas Funcionales por Actividad

### 3.1 Actividad 1: Tareas Secuenciales

#### Prueba Básica
- [ ] Card "Actividad 1" abre la pantalla correcta
- [ ] Título: "Simulador de Tareas Secuenciales"
- [ ] Botón "Iniciar" visible
- [ ] Botón "Limpiar" visible

#### Ejecución
- [ ] Al presionar "Iniciar", estado cambia a "Ejecutando..."
- [ ] Aparece log: "🔐 Iniciando login..."
- [ ] Después de ~2s: "✓ Login completado"
- [ ] Aparece: "👤 Cargando perfil de usuario..."
- [ ] Después de ~1.5s: "✓ Perfil cargado"
- [ ] Aparece: "⚙️ Cargando preferencias..."
- [ ] Después de ~1s: "✓ Preferencias cargadas"
- [ ] Tiempo total mostrado: ~4500ms
- [ ] Estado final: "Finalizado"

#### Validaciones
- [ ] Timestamps muestran orden secuencial
- [ ] Botón "Limpiar" limpia el output
- [ ] Pueden ejecutarse múltiples veces
- [ ] UI no se congela durante ejecución
- [ ] Back button regresa al menú principal

### 3.2 Actividad 2: Temporizador

#### Prueba Básica
- [ ] Card "Actividad 2" abre la pantalla correcta
- [ ] Título: "Temporizador No Bloqueante"
- [ ] Contador grande visible (muestra "0 segundos")
- [ ] Botón "Iniciar" visible
- [ ] Botón "Cancelar" visible (deshabilitado inicialmente)

#### Ejecución Normal
- [ ] Al presionar "Iniciar":
  - [ ] Estado cambia a "Ejecutando..."
  - [ ] Botón "Iniciar" se deshabilita
  - [ ] Botón "Cancelar" se habilita
- [ ] Contador aumenta cada segundo
- [ ] Log muestra "⏰ Segundo 1", "⏰ Segundo 2", etc.
- [ ] UI permanece responsive (se puede scrollear el log)
- [ ] Después de 30 segundos, se detiene automáticamente
- [ ] Estado final: "Finalizado"

#### Prueba de Cancelación ⭐
- [ ] Iniciar temporizador
- [ ] Esperar ~10 segundos
- [ ] Presionar "Cancelar"
- [ ] Temporizador se detiene inmediatamente
- [ ] Aparece log: "🛑 Cancelación solicitada..."
- [ ] Aparece: "❌ Temporizador cancelado"
- [ ] Estado: "Cancelado"
- [ ] Botones vuelven a estado inicial

#### Validaciones
- [ ] No hay delay entre presionar "Cancelar" y detención
- [ ] Puede reiniciarse después de cancelar
- [ ] Rotación de pantalla mantiene estado
- [ ] No hay memory leak (verificar en profiler si es posible)

### 3.3 Actividad 3: API Simulada

#### Prueba Básica
- [ ] Card "Actividad 3" abre la pantalla correcta
- [ ] Título: "Simulación de API Lenta"
- [ ] Botones "Iniciar" y "Limpiar" visibles

#### Ejecución Exitosa
- [ ] Al presionar "Iniciar":
  - [ ] Estado: "Ejecutando..."
  - [ ] Log: "🌐 Iniciando llamada a API..."
  - [ ] Log: "📡 Conectando al servidor..."
- [ ] Esperar 1.5-3 segundos (variable)
- [ ] Log: "✓ Respuesta recibida en XXXXms"
- [ ] Se muestran datos:
  - [ ] ID numérico
  - [ ] Nombre de usuario
  - [ ] Email
  - [ ] Fecha de creación
  - [ ] Estado Premium (Sí/No)
- [ ] Estado: "Finalizado"

#### Prueba de Errores (20% probabilidad)
- [ ] Ejecutar múltiples veces (5-10 intentos)
- [ ] En al menos 1 intento, debe aparecer:
  - [ ] Log: "❌ Error después de XXXXms"
  - [ ] Mensaje de error: "Error de red: Timeout"
  - [ ] Estado: "Error"

#### Validaciones
- [ ] Tiempo de respuesta varía entre ejecuciones
- [ ] Datos aleatorios en cada ejecución exitosa
- [ ] Formato de salida limpio y legible

### 3.4 Actividad 4: Pronóstico del Clima

#### Prueba Básica
- [ ] Card "Actividad 4" abre la pantalla correcta
- [ ] Título: "Pronóstico del Clima Concurrente"
- [ ] Botones "Iniciar" y "Limpiar" visibles

#### Ejecución y Medición de Concurrencia ⭐
- [ ] Al presionar "Iniciar":
  - [ ] Estado: "Ejecutando..."
  - [ ] Log: "🌤️ Obteniendo pronóstico del clima..."
  - [ ] Log: "ℹ️ Usando async/await para concurrencia"
- [ ] Aparecen 3 logs casi simultáneamente:
  - [ ] "🌡️ Solicitando temperatura..."
  - [ ] "💧 Solicitando humedad..."
  - [ ] "💨 Solicitando viento..."
- [ ] Esperar ~2 segundos (NO 4.5)
- [ ] Resultados aparecen:
  - [ ] "✓ Temperatura: XX°C"
  - [ ] "✓ Humedad: XX%"
  - [ ] "✓ Viento: XX km/h"

#### Análisis de Tiempo ⭐
- [ ] Se muestra "⏱️ Tiempo total: ~2000ms"
- [ ] Aparece análisis:
  - [ ] Tiempo secuencial esperado: ~4500ms
  - [ ] Tiempo concurrente real: ~2000ms
  - [ ] Ahorro: ~2500ms
- [ ] Mensaje: "✨ Las tareas se ejecutaron EN PARALELO"

#### Validaciones Críticas
- [ ] Tiempo total debe ser ~2000ms (±300ms)
- [ ] NO debe ser ~4500ms
- [ ] Los 3 "Solicitando..." deben aparecer casi al mismo tiempo
- [ ] Diferencia entre timestamps de inicio < 10ms

### 3.5 Actividad 5: Descargador de Archivos

#### Prueba Básica
- [ ] Card "Actividad 5" abre la pantalla correcta
- [ ] Título: "Descargador de Archivos"
- [ ] Botones "Iniciar" y "Cancelar" visibles
- [ ] Barra de progreso visible (0%)

#### Ejecución Completa
- [ ] Al presionar "Iniciar":
  - [ ] Estado: "Ejecutando..."
  - [ ] Botón "Cancelar" se habilita
- [ ] Aparecen 5 logs casi simultáneamente:
  - [ ] "⬇️ Descargando document.pdf..."
  - [ ] "⬇️ Descargando image.jpg..."
  - [ ] "⬇️ Descargando video.mp4..."
  - [ ] "⬇️ Descargando audio.mp3..."
  - [ ] "⬇️ Descargando archive.zip..."
- [ ] Logs de progreso aparecen intercalados:
  - [ ] "document.pdf: 25%"
  - [ ] "image.jpg: 20%"
  - [ ] etc. (no secuencial)
- [ ] Barra de progreso aumenta gradualmente
- [ ] Archivos completan en orden variable
- [ ] Mensaje final: "🎉 Todas las descargas finalizadas!"
- [ ] Barra de progreso: 100%
- [ ] Estado: "Finalizado"

#### Prueba de Cancelación ⭐
- [ ] Iniciar descargas
- [ ] Esperar hasta ~50% de progreso
- [ ] Presionar "Cancelar"
- [ ] Todas las descargas se detienen inmediatamente
- [ ] Aparece: "🛑 Cancelando todas las descargas..."
- [ ] Múltiples logs: "❌ [archivo] cancelado"
- [ ] Estado: "Cancelado"
- [ ] Barra de progreso se detiene en valor actual

#### Validaciones
- [ ] Progreso de diferentes archivos aparece intercalado
- [ ] Algunos archivos terminan antes que otros
- [ ] Cancelar detiene TODOS los archivos
- [ ] Progreso nunca excede 100%

### 3.6 Actividad 6: Sistema de Notificaciones

#### Prueba Básica
- [ ] Card "Actividad 6" abre la pantalla correcta
- [ ] Título: "Sistema de Notificaciones"
- [ ] Botones "Iniciar" y "Cancelar" visibles

#### Ejecución Normal
- [ ] Al presionar "Iniciar":
  - [ ] Estado: "Ejecutando..."
  - [ ] Botón "Cancelar" se habilita
- [ ] Cada 3 segundos aparece una notificación:
  - [ ] "[1] 📧 Tienes un nuevo mensaje"
  - [ ] "[2] 🔔 Recordatorio: ..."
  - [ ] etc.
- [ ] Contador incrementa: [1], [2], [3]...
- [ ] Notificaciones son variadas (diferentes mensajes)
- [ ] Después de 20 notificaciones:
  - [ ] Aparece: "ℹ️ Límite de demo alcanzado"
  - [ ] Sistema se detiene automáticamente
  - [ ] Estado: "Finalizado"

#### Prueba de Cancelación ⭐
- [ ] Iniciar sistema
- [ ] Esperar ~5 notificaciones
- [ ] Presionar "Cancelar"
- [ ] Sistema se detiene inmediatamente
- [ ] Log: "🛑 Deteniendo sistema..."
- [ ] Log: "❌ Sistema de notificaciones detenido"
- [ ] Estado: "Cancelado"
- [ ] No aparecen más notificaciones

#### Validaciones
- [ ] Intervalo entre notificaciones es constante (~3s)
- [ ] Mensajes son variados
- [ ] Puede reiniciarse después de cancelar
- [ ] Contador se reinicia al iniciar nuevamente

## 🔍 Fase 4: Pruebas de Integración

### 4.1 Navegación
- [ ] Desde cualquier actividad, Back button regresa al menú
- [ ] Menú principal siempre muestra las 6 opciones
- [ ] Se puede entrar y salir de actividades múltiples veces
- [ ] No hay crashes al navegar rápidamente

### 4.2 Rotación de Pantalla
- [ ] Rotar dispositivo en menú principal → layout se adapta
- [ ] Rotar durante ejecución de Activity 1 → continúa
- [ ] Rotar durante timer (Activity 2) → contador continúa
- [ ] Rotar durante descargas (Activity 5) → continúan
- [ ] Estado y logs se mantienen después de rotación

### 4.3 Ciclo de Vida
- [ ] Presionar Home durante una actividad
- [ ] Regresar a la app
- [ ] Si estaba ejecutando, debe continuar o mostrar estado final
- [ ] No debe haber crash al regresar

### 4.4 Multi-instancia
- [ ] Ejecutar Activity 2 (timer), dejar corriendo
- [ ] Ir al menú principal (Back)
- [ ] Abrir otra actividad
- [ ] Timer debe haberse cancelado automáticamente

## 🎨 Fase 5: UI/UX

### 5.1 Diseño
- [ ] Material Design cards se ven bien
- [ ] Colores apropiados
- [ ] Textos legibles
- [ ] Botones tienen tamaño apropiado (táctil)
- [ ] Scroll funciona en logs largos

### 5.2 Responsive
- [ ] UI no se congela en ninguna actividad
- [ ] Scroll siempre funciona
- [ ] Botones siempre responden
- [ ] Rotación es suave

### 5.3 Estados
- [ ] Botones se deshabilitan/habilitan apropiadamente
- [ ] Estados visuales son claros
- [ ] No hay confusión sobre qué está pasando

## 📊 Fase 6: Performance y Calidad

### 6.1 Performance
- [ ] App inicia rápido (< 3 segundos)
- [ ] Navegación es fluida (60 FPS si es posible)
- [ ] No hay lag al ejecutar actividades
- [ ] Logs se renderizan sin lag

### 6.2 Memory
- [ ] (Opcional) Verificar en Android Profiler:
  - [ ] No hay memory leaks al entrar/salir de actividades
  - [ ] Memory se libera al cancelar operaciones
  - [ ] Heap growth es razonable

### 6.3 Batería
- [ ] App no consume batería excesiva en background
- [ ] Todas las corutinas se cancelan al salir

## 📚 Fase 7: Documentación

### 7.1 README.md
- [ ] Abre sin errores
- [ ] Instrucciones son claras
- [ ] Enlaces funcionan
- [ ] Formato Markdown correcto

### 7.2 kotlin_coroutines_activities.md
- [ ] Abre sin errores
- [ ] Contiene explicaciones para las 6 actividades
- [ ] Snippets de código son legibles
- [ ] Formato Markdown correcto

### 7.3 VISUAL_GUIDE.md
- [ ] Abre sin errores
- [ ] ASCII art se ve bien
- [ ] Ejemplos coinciden con output real

### 7.4 Código
- [ ] Comentarios en código son útiles
- [ ] Nombres de variables/funciones son claros
- [ ] Estructura de packages es lógica

## ✅ Resumen de Pruebas Críticas

Estas son las pruebas MÁS IMPORTANTES que DEBEN pasar:

1. ⭐ **Activity 2 Cancellation**: Timer se detiene inmediatamente
2. ⭐ **Activity 4 Timing**: Tiempo ~2s no ~4.5s (concurrencia funciona)
3. ⭐ **Activity 5 Cancellation**: Todas las descargas se detienen
4. ⭐ **Activity 6 Cancellation**: Sistema se detiene inmediatamente
5. ⭐ **No UI Freeze**: UI permanece responsive en todas las actividades
6. ⭐ **Rotation**: Estado se mantiene después de rotar pantalla

## 📝 Reporte de Bugs

Si encuentras un bug, reporta:
- [ ] Actividad afectada
- [ ] Pasos para reproducir
- [ ] Comportamiento esperado
- [ ] Comportamiento actual
- [ ] Logs de error (si hay)
- [ ] Versión de Android
- [ ] Modelo de dispositivo

## 🎓 Verificación de Aprendizaje

Después de probar, deberías poder explicar:
- [ ] ¿Qué es una corutina?
- [ ] ¿Diferencia entre `launch` y `async`?
- [ ] ¿Por qué NO usar `GlobalScope`?
- [ ] ¿Cómo cancelar una corutina?
- [ ] ¿Qué es `suspend fun`?
- [ ] ¿Por qué la concurrencia es más rápida?

---

**Estado**: ☐ No iniciado | ⏳ En progreso | ✅ Completado

**Fecha de prueba**: _______________

**Probado por**: _______________

**Resultado final**: ☐ PASS | ☐ FAIL

**Notas adicionales**:
