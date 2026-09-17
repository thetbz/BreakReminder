# Work Break Reminder

Una aplicación Java que funciona como alarma automática de descansos en el trabajo. Ejecuta ciclos continuos de 45 minutos de trabajo seguidos de 2 minutos de descanso, sin necesidad de activación manual.

## Características

✅ **Ciclos Automáticos**: 45 minutos trabajando + 2 minutos de descanso  
✅ **Sin Intervención Manual**: Una vez iniciada, funciona automáticamente  
✅ **Notificaciones Visuales**: Ventana emergente cuando llega la hora de descansar  
✅ **Alertas de Sonido**: Tonos diferentes para descanso y reanudación del trabajo  
✅ **Interfaz Gráfica**: GUI intuitiva con temporizador visible  
✅ **Registros en Consola**: Seguimiento de eventos en tiempo real  

## Requisitos

- Java 11 o superior
- Maven 3.6+ (para compilar)

## Instalación y Compilación

### 1. Compilar con Maven

```bash
cd "c:\Users\esteb\java workspace\BreakReminder"
mvn clean package
```

### 2. Ejecutar desde JAR

```bash
java -jar target/BreakReminder.jar
```

O ejecutar directamente:

```bash
mvn exec:java -Dexec.mainClass="com.breakreminder.Main"
```

## Uso

1. **Inicia la aplicación**: Se abrirá una ventana con la interfaz gráfica
2. **Haz clic en "Start"**: Comienza el ciclo automático de 45 minutos
3. **Espera el recordatorio**: Cuando se cumpla el tiempo, aparecerá una alarma
4. **Descansa 2 minutos**: La aplicación te lo indicará automáticamente
5. **Vuelve al trabajo**: Automáticamente iniciará otro ciclo de 45 minutos

La aplicación continuará ciclando automáticamente hasta que hagas clic en "Stop".

## Estructura del Proyecto

```
BreakReminder/
├── src/main/java/com/breakreminder/
│   ├── Main.java              # Punto de entrada
│   ├── BreakReminder.java     # Lógica principal del temporizador
│   ├── ReminderGUI.java       # Interfaz gráfica con Swing
│   └── SoundAlert.java        # Sistema de alertas sonoras
├── pom.xml                     # Configuración de Maven
├── .gitignore                  # Archivos a ignorar en Git
└── README.md                   # Este archivo
```

## Clases Principales

### `Main.java`
Punto de entrada de la aplicación. Inicializa e inicia el recordatorio de descansos.

### `BreakReminder.java`
Gestiona la lógica de ciclos de trabajo y descanso. Incluye:
- Temporizador de 45 minutos para el trabajo
- Temporizador de 2 minutos para descansos
- Transiciones automáticas entre ciclos
- Notificaciones de cambio de estado

### `ReminderGUI.java`
Interfaz gráfica construida con Swing que muestra:
- Temporizador grande y visible
- Estado actual de la sesión
- Botones Start/Stop para control manual
- Notificaciones emergentes

### `SoundAlert.java`
Genera y reproduce alertas sonoras:
- Alarma de 2 tonos para descanso (800 Hz)
- Alarma de 3 tonos para reanudación (1000 Hz)

## Configuración Personalizada

Para cambiar los tiempos de trabajo y descanso, edita las constantes en `BreakReminder.java`:

```java
private static final long WORK_TIME_MS = 45 * 60 * 1000;   // Tiempo de trabajo en ms
private static final long BREAK_TIME_MS = 2 * 60 * 1000;   // Tiempo de descanso en ms
```

Ejemplos:
- 30 minutos de trabajo: `30 * 60 * 1000`
- 5 minutos de descanso: `5 * 60 * 1000`

## Licencia

Libre de usar y modificar.

## Autor

Esteban Arana
Creado con Java Swing y Threads para la gestión de ciclos automáticos.
