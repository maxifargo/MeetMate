# MeetMate (primer avance)

Este proyecto en su primer avance implementa una agenda interactiva de reuniones en Java, aplicando conceptos básicos de Programación Orientada a Objetos (POO) y UML como:  
- Clases y atributos privados con getters y setters.  
- Sobrecarga de métodos (`agregarReunion`).  
- Uso de colecciones (`List` y `Map`) para almacenar y organizar reuniones.  
- Menú interactivo con `Scanner` para gestionar las reuniones desde consola.  
- Agrupación de reuniones por etiquetas.  

---

## Características
1. Insertar nuevas reuniones con:
   - Fecha (dd/mm/yyyy)  
   - Hora (HH:mm)  
   - Descripción  
   - Etiqueta (ej: Trabajo, Personal, etc.)  

2. Mostrar todas las reuniones registradas.  

3. Mostrar reuniones agrupadas por etiquetas.  

4. Salir del programa.  

---

## Estructura de Clases
- **Reunion**  
  Representa una reunión con sus datos principales.  
  - Atributos: `fecha`, `hora`, `descripcion`, `etiqueta`.  
  - Métodos: getters/setters, `toString()`.  

- **Agenda**  
  Gestiona las reuniones en memoria.  
  - Almacena reuniones en una lista (`List`) y en un mapa (`Map`) para agruparlas por etiquetas.  
  - Métodos:  
    - `agregarReunion(...)` (sobrecargado)  
    - `mostrarReuniones()`  
    - `mostrarReunionesPorEtiqueta()`  

- **Main**  
  Clase principal con el menú interactivo.  

---

## Ejecución

Compilar y ejecutar en consola:

```bash
javac Main.java
java Main
```
## Ejemplo de uso
--- MENU AGENDA ---
1. Insertar nueva reunión
2. Mostrar todas las reuniones
3. Mostrar reuniones por etiqueta
0. Salir
Seleccione opción: 2

Listado de reuniones:
23/08/2025 10:00 - Reunión de proyecto [Trabajo]
24/08/2025 18:00 - Cita médica [Personal]

Notas

Se busca agregar funciones y completar el código en los próximos hitos.
