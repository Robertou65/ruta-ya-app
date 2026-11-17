# RutaYa

Aplicación Android nativa para el registro e ingreso local de repartidores. Construida con Kotlin y Jetpack Compose.

## Funcionalidades

- Pantalla de presentación con logo propio, descripción y botón de continuación.
- Registro con datos personales y del vehículo.
- Inicio de sesión por documento y contraseña.
- Persistencia local en SQLite (`SQLiteOpenHelper`), sin servidor.
- Perfil del repartidor y botón que finaliza la tarea de Android.

## Abrir y ejecutar

1. Abre esta carpeta en Android Studio.
2. Espera la sincronización de Gradle.
3. Ejecuta `app` en un emulador o dispositivo con Android 7.0 o posterior.

La contraseña se guarda como hash SHA-256, no como texto plano. Esta versión es educativa; para producción se recomienda cifrado reforzado y Android Keystore.
