# AppTuRecetario

AppTuRecetario es una aplicación Android moderna que permite a los usuarios explorar y guardar sus recetas favoritas. Desarrollada con las últimas tecnologías y siguiendo las mejores prácticas de desarrollo Android.

## 🌟 Características

- Búsqueda de recetas en tiempo real
- Visualización detallada de recetas con ingredientes e instrucciones
- Sistema de favoritos
- Persistencia local de datos
- Soporte offline
- Modo oscuro
- Pantalla de onboarding para nuevos usuarios
- Diseño Material 3

## 🏗 Arquitectura

El proyecto sigue Clean Architecture con MVVM y está organizado en las siguientes capas:

```
com.example.appturecetario/
├── data/
│   ├── api/          # Servicios de red y DTOs
│   ├── database/     # Base de datos local y entidades
│   └── repository/   # Implementación de repositorios
├── domain/
│   ├── model/        # Modelos de dominio
│   ├── repository/   # Interfaces de repositorio
│   └── usecase/      # Casos de uso
├── di/               # Módulos de inyección de dependencias
└── presentation/
    ├── screens/      # Pantallas de la aplicación
    ├── components/   # Componentes reutilizables
    └── theme/        # Estilos y temas
```

### Tecnologías Principales

- **UI**: Jetpack Compose
- **Arquitectura**: MVVM + Clean Architecture
- **Inyección de Dependencias**: Hilt
- **Base de Datos**: Room
- **Networking**: Retrofit
- **Imágenes**: Coil
- **Concurrencia**: Coroutines + Flow
- **Testing**: JUnit

## 🌐 API

La aplicación consume la [Spoonacular API](https://spoonacular.com/food-api), que proporciona:
- Búsqueda de recetas
- Información detallada de recetas
- Ingredientes
- Instrucciones de preparación

## 📱 Pantallas

1. **Splash Screen**
   - Logo de la aplicación
   - Duración de 2 segundos

2. **Onboarding**
   - 3 pantallas de introducción
   - Solo se muestra en el primer inicio

3. **Lista de Recetas**
   - Búsqueda en tiempo real
   - Pull to refresh
   - Marcado de favoritos
   - Vista de lista con imágenes

4. **Detalle de Receta**
   - Imagen de la receta
   - Lista de ingredientes
   - Instrucciones paso a paso
   - Botón de favorito

## 💾 Persistencia

- **Room Database** para almacenamiento local
- **DataStore** para preferencias de usuario
- Soporte offline first
- Cache de recetas y favoritos

## 🔄 Estados de UI

La aplicación maneja diferentes estados de UI:
- Loading
- Success
- Error
- Empty State

## 🚀 Configuración del Proyecto

1. Clona el repositorio
2. Obtén una API key de [Spoonacular](https://spoonacular.com/food-api)
3. Agrega tu API key en el archivo `local.properties`:
   ```
   spoonacular.api.key=TU_API_KEY
   ```
4. Sincroniza el proyecto con Gradle
5. ¡Ejecuta la aplicación!

## 📝 Requisitos

- Android Studio Arctic Fox o superior
- JDK 11
- Android 6.0 (API level 23) o superior
- Gradle 7.0 o superior

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.
