# Proyecto SIA – Agenda de Reuniones

Integrantes


Joaquín Muñoz


Joaquín Tapia


Maximiliano Fargo

Aplicación de escritorio en Java (Swing) para la gestión de reuniones, desarrollada como parte del curso Programación Avanzada.  
Permite crear, editar, eliminar, filtrar y guardar reuniones en un archivo CSV, con soporte para etiquetas, usuarios y validación de datos.

---

## Funcionalidades principales

- CRUD de reuniones:
  - Crear, editar y eliminar reuniones con ID, usuario, fecha, hora y etiqueta.
- Filtros:
  - Filtrar por etiqueta.
  - Filtrar por rango de fechas (ejemplo: 01/10/2025 – 15/10/2025).
- Búsquedas:
  - Buscar reuniones por ID.
  - Mostrar todas las reuniones.
- Gestión de usuarios:
  - Renombrar usuarios.
  - Eliminar todas las reuniones asociadas a un usuario.
- Persistencia:
  - Cargar reuniones desde archivo CSV al iniciar.
  - Guardar automáticamente en archivo CSV al cerrar.

---

## Requisitos de instalación

- Java 17 o superior.
- Biblioteca estándar de Java (Swing, AWT).
- Archivo `agenda.csv` en el directorio raíz (se crea automáticamente si no existe).

---

## Instrucciones de uso

1. Compilar los archivos fuente:

   ```bash
   javac agenda/*.java
Ejecutar la aplicación:

java agenda.Main
Al iniciar, se cargan las reuniones desde agenda.csv (si existe).
Al cerrar la ventana, los cambios se guardan automáticamente.

Estructura del proyecto
agenda/

 ├── Main.java                 # Punto de entrada de la aplicación

 ├── MainWindow.java           # Ventana principal con tabla y botones
 
 ├── Agenda.java               # Lógica principal de gestión de reuniones
 
 ├── Reunion.java              # Clase POJO de reunión
 
 ├── ReunionDialog.java        # Diálogo para crear/editar reuniones
 
 ├── FechaInvalidaException.java
 
 ├── HoraInvalidaException.java
 
 └── agenda.csv                # Archivo CSV de persistencia (se crea al ejecutar)
 
 
Ejemplo de formato CSV
El archivo agenda.csv almacena las reuniones en el siguiente formato:

ID,Usuario,Fecha,Hora,Etiqueta

1,Carlos,01/10/2025,09:00,Trabajo
2,Ana,02/10/2025,10:30,Estudio
3,Luis,03/10/2025,15:00,Deporte

