# Actividades de Corrutinas en Kotlin - Android

Una colección de 6 actividades prácticas para aprender corrutinas en Kotlin con interfaces de usuario interactivas.

## 📱 Características

- **6 Actividades Progresivas**: Desde conceptos básicos hasta avanzados
- **UI Interactiva**: Cada actividad muestra resultados en pantalla con logs detallados
- **Cancelación**: Actividades 2, 5 y 6 soportan cancelación en tiempo real
- **Medición de Tiempo**: La actividad 4 demuestra beneficios de concurrencia
- **Buenas Prácticas**: Usa `viewModelScope` y `lifecycleScope`, evita `GlobalScope`
- **Documentación Completa**: Ver [kotlin_coroutines_activities.md](kotlin_coroutines_activities.md)

## 🎯 Actividades Incluidas

1. **Tareas Secuenciales** - `suspend fun`, `delay`, ejecución secuencial
2. **Temporizador** - `launch`, `Job.cancel()`, bucles no bloqueantes
3. **API Simulada** - Repository pattern, manejo de errores
4. **Pronóstico Concurrente** - `async`/`await`, `measureTimeMillis`
5. **Descargador de Archivos** - Múltiples `launch`, `join()`, progreso
6. **Sistema de Notificaciones** - `while(isActive)`, lifecycle management

## 🛠️ Requisitos

- Android Studio Otter
- Kotlin 1.17.0+
- Android SDK 36+
- Gradle 8.1+

## 📦 Dependencias

```gradle
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
}
```

## 🚀 Cómo Ejecutar

### Opción 1: Android Studio (Recomendado)

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/ByMisterJ/Actividades-corrutinas-Kotlin.git
   cd Actividades-corrutinas-Kotlin
   ```

2. Abrir el proyecto en Android Studio

3. Esperar a que Gradle sincronice las dependencias

4. Conectar un dispositivo Android o iniciar un emulador

5. Presionar **Run** (▶️) o `Shift + F10`

### Opción 2: Línea de Comandos

```bash
# Asegurar permisos de ejecución
chmod +x gradlew

# Compilar
./gradlew assembleDebug

# Instalar en dispositivo conectado
./gradlew installDebug
```

## ⚠️ Nota sobre Build en CI/CD

Si encuentras problemas de conectividad con `dl.google.com` en entornos CI/CD, puedes:

1. **Usar un proxy o mirror de Maven**:
   ```kotlin
   // En settings.gradle.kts
   repositories {
       maven { url = uri("https://maven.aliyun.com/repository/google") }
       google()
       mavenCentral()
   }
   ```

2. **Cachear dependencias**: Configura tu CI para cachear `~/.gradle/caches`

3. **Usar versión local**: Las dependencias ya sincronizadas localmente funcionarán

## 📖 Documentación Técnica

Consulta [kotlin_coroutines_activities.md](kotlin_coroutines_activities.md) para:
- Explicaciones detalladas de cada actividad
- Conceptos de corrutinas con ejemplos de código
- Diagramas de flujo
- Qué observar en cada UI
- Mejores prácticas y anti-patrones

## 🎓 Conceptos Aprendidos

| Concepto | Actividad(es) |
|----------|---------------|
| `suspend fun` | 1, 3 |
| `launch` | 2, 5, 6 |
| `async`/`await` | 4 |
| `Job.cancel()` | 2, 5, 6 |
| `Job.join()` | 5 |
| `while(isActive)` | 6 |
| `delay()` | Todas |
| `viewModelScope` | Todas |
| `lifecycleScope` | Todas |
| Repository pattern | 3 |
| Concurrencia | 4, 5 |

## 📸 Capturas de Pantalla

*(Se añadirán después de compilar y ejecutar en dispositivo)*

## 🏗️ Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/actividades_corrutinas_kotlin/
│   │   ├── activities/
│   │   │   ├── SequentialTasksActivity.kt
│   │   │   ├── TimerActivity.kt
│   │   │   ├── ApiSimulationActivity.kt
│   │   │   ├── WeatherForecastActivity.kt
│   │   │   ├── FileDownloaderActivity.kt
│   │   │   └── NotificationSystemActivity.kt
│   │   ├── viewmodels/
│   │   │   ├── SequentialTasksViewModel.kt
│   │   │   ├── TimerViewModel.kt
│   │   │   ├── ApiSimulationViewModel.kt
│   │   │   ├── WeatherForecastViewModel.kt
│   │   │   ├── FileDownloaderViewModel.kt
│   │   │   └── NotificationSystemViewModel.kt
│   │   └── MainActivity.kt
│   └── res/
│       └── layout/
│           ├── activity_main.xml
│           └── [6 activity layouts]
└── kotlin_coroutines_activities.md
```

## 🤝 Contribuir

Las contribuciones son bienvenidas. Por favor:
1. Haz fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👨‍💻 Autor

**ByMisterJ**

## 🔗 Referencias

- [Kotlin Coroutines Official Guide](https://kotlinlang.org/docs/coroutines-guide.html)
- [Android Coroutines Best Practices](https://developer.android.com/kotlin/coroutines/coroutines-best-practices)
- [ViewModel with Coroutines](https://developer.android.com/topic/libraries/architecture/coroutines)

---

⭐ Si este proyecto te ayudó a aprender corrutinas, considera darle una estrella!
