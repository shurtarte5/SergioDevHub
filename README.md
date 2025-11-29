# SergioDevHub

Aplicación Android básica que muestra películas populares usando la API de The Movie Database (TMDB).

## Características

- Lista de películas populares
- Carga de imágenes con Coil
- Arquitectura MVVM simple
- Inyección de dependencias con Hilt
- Jetpack Compose UI
- Retrofit para llamadas API

## Tecnologías

- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - UI moderna y declarativa
- **Material 3** - Sistema de diseño
- **Retrofit** - Cliente HTTP para llamadas API
- **Hilt** - Inyección de dependencias
- **Coil** - Carga de imágenes
- **Coroutines & Flow** - Programación asíncrona
- **MVVM** - Arquitectura de presentación

## Requisitos

- Android Studio Hedgehog o superior
- JDK 11 o superior
- Dispositivo o emulador con Android API 24+

## Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/shurtarte5/SergioDevHub.git
cd SergioDevHub
```

2. Abrir el proyecto en Android Studio

3. Sincronizar Gradle

4. Ejecutar la aplicación (el proyecto ya incluye el API key de TMDB)

## Estructura del Proyecto

```
app/src/main/java/com/sergio/sergiodevhub/
├── data/                          # Capa de datos
│   ├── dto/                       # Data Transfer Objects
│   ├── network/                   # Configuración de Retrofit
│   └── repository/                # Implementación del repositorio
├── domain/                        # Lógica de negocio
│   ├── model/                     # Modelos de dominio
│   └── repository/                # Interfaces del repositorio
├── presentation/                  # Capa de presentación
│   ├── ui/                        # Pantallas Compose
│   ├── viewmodel/                 # ViewModels
│   └── MainActivity.kt
└── di/                           # Módulos de Hilt
```

## API

Este proyecto usa [The Movie Database (TMDB) API](https://www.themoviedb.org/documentation/api) para obtener información de películas.

## Licencia

Este es un proyecto de portafolio con fines educativos.
