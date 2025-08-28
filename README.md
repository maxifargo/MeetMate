# MeetMate (Primer Avance) ADA-3

## Integrantes

Joaquín Muñoz

Joaquín Tapia

Maximiliano Fargo

## Introducción

Este proyecto implementa una agenda interactiva de reuniones en Java, aplicando conceptos de Programación Orientada a Objetos y UML.  
Actualmente es un primer avance, y se irán agregando más funcionalidades en próximos hitos.
Este proyecto es de naturaleza modular, es decir, las clases vienen separadas en distintos archivos de códigos .java.

---

## Funcionalidades

- **Insertar nuevas reuniones** con:
  - Fecha (`dd/mm/yyyy`)
  - Hora (`HH:mm`)
  - Descripción
  - Etiqueta (ej: *Trabajo, Personal, Estudio*)
- **Mostrar todas las reuniones registradas.**
- **Mostrar reuniones agrupadas por etiquetas.**
- **Salir del programa.**

---

## Conceptos aplicados

- Clases y atributos privados con **getters y setters**.  
- **Sobrecarga de métodos** (`agregarReunion`).  
- Uso de **colecciones (`List` y `Map`)** para organizar reuniones.  
- **Menú interactivo** con `Scanner` para gestionar reuniones desde consola.  
- **Agrupación por etiquetas** para una mejor organización.  
- **Separación del código en múltiples archivos** (`Agenda.java`, `Reunion.java`, `Main.java`) siguiendo buenas prácticas.  

---

## Estructura de Clases

### `Reunion`
Representa una reunión con sus datos principales.  
- **Atributos:** `fecha`, `hora`, `descripcion`, `etiqueta`.  
- **Métodos:** `getters/setters`, `toString()`.

### `Agenda`
Gestiona las reuniones en memoria.  
- **Atributos:** lista de reuniones (`List`) y mapa de etiquetas (`Map`).  
- **Métodos:**  
  - `agregarReunion(...)` (sobrecargado)  
  - `mostrarReuniones()`  
  - `mostrarReunionesPorEtiqueta()`

### `Main`
Clase principal con el menú interactivo para el usuario.  

---

## ▶ Ejecución

Compilar y ejecutar en consola:

```
bash
javac Main.java
java Main
📋 Ejemplo de uso
markdown
Copiar código
--- MENU AGENDA ---

1. Insertar nueva reunión
2. Mostrar todas las reuniones
3. Mostrar reuniones por etiqueta
4. Salir

Seleccione opción: 2

Listado de reuniones:
23/08/2025 10:00 - Reunión de proyecto [Trabajo]
24/08/2025 18:00 - Cita médica [Personal]


```

Notas:
Este es un primer avance del proyecto.

Se agregarán más funcionalidades en próximos hitos.

El repositorio incluye además un diagrama UML en formato .png y .puml para la visualización de la estructura de clases.
