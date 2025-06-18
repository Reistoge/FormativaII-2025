# 🧪 Segunda Evaluación Formativa PSP-I-2025

Este proyecto corresponde a una **prueba formativa de programación** donde se implementa una arquitectura en capas utilizando **Java, JPA con SQLite**, pruebas automatizadas con **JUnit 4 y Mockito**, y posteriormente se realiza una refactorización a una arquitectura **reactiva** basada en **Vert.x** con el patrón *Publish/Subscribe*.

---

## 🎯 Objetivos

- Implementar un sistema en capas usando JPA y SQLite.
- Escribir reglas de verificación arquitectural.
- Diseñar casos de prueba unitarios con JUnit 4 y Mockito.
- Refactorizar el sistema a un modelo basado en Vert.x y EventBus.

---

## 🧱 Estructura del proyecto en capas

 Carpeta / Paquete             | Contenido principal                                                            |
|------------------------------|--------------------------------------------------------------------------------|
| `dominio`                    | Clase `Tarea.java` con anotaciones JPA (`@Entity`)                             |
| `repositorio`                | Interfaz `RepositorioTarea` y clase `RepositorioTareaImpl` con `EntityManager` |
| `servicio`                   | Lógica de negocio en `ServicioTarea`                                           |
| `consola`                    | Clase `MainApp` con método `main()`                                            |
| `vertx`                      | Implementación basada en Vert.x y EventBus                                     |
| `test/servicio`              | Tests unitarios con JUnit 4 y Mockito                                          |
---
## 🧩 Reglas Arquitecturales Requeridas (2)

Se deben implementar y verificar al menos las siguientes **2 reglas arquitecturales**, utilizando alguna herramienta de análisis estático en Java de su competencia.

### 🔒 Regla 1: La capa `servicio` no debe acceder a clases de la capa `persistencia` directamente

> La lógica de negocio debe usar solo la interfaz `RepositorioTarea` sin depender de detalles de implementación como `RepositorioTareaImpl`.

- **Propósito**: Evitar acoplamiento entre la lógica de negocio (`servicio`) y la implementación concreta de persistencia (`repositorio`).
- **Expresado informalmente**:
  > “`servicio` solo puede depender de interfaces del paquete `repositorio`, no de clases concretas”.

### 🚫 Regla 2: La capa `dominio` debe ser pura

> Las clases del paquete `dominio` no deben depender de ninguna otra capa del sistema, como `servicio`, `repositorio`, `consola`, ni `vertx`.

- **Propósito**: Mantener el modelo de dominio completamente desacoplado del resto de la aplicación, permitiendo su reutilización, testeo y mantenimiento independiente.
- **Expresado informalmente**:
  > “El dominio no debe conocer a nadie más.”

---
## Pruebas unitarias 

### 🧪 Pruebas unitarias sin mockito

Se deben implementar un total de **7 pruebas unitarias** usando **JUnit 4**. Estas pruebas evalúan directamente la lógica de negocio de la clase `ServicioTareas`, sin necesidad de ejecutar contra una base de datos real.

**Clase bajo prueba**: `ServicioTareas`  
**Dependencias reales (sin Mockito)** 

| # | Nombre del test            | Propósito                                                   |
|--:|----------------------------|--------------------------------------------------------------|
| 1 | `testCrearTareaValida`     | Verifica que se crea una tarea con los datos correctos       |
| 2 | `testCrearTareaFechaVacia` | Verifica que se puede crear una tarea sin fecha de vencimiento |
| 3 | `testListarTareasVacias`   | Verifica que al inicio no hay tareas almacenadas             |
| 4 | `testMarcarCompletada`     | Verifica que el método cambia el estado de la tarea a `true` |
| 5 | `testEliminarTarea`        | Verifica que una tarea agregada puede ser luego eliminada    |

| # | Nombre del test      | Propósito                                                        |
|--:|----------------------|-------------------------------------------------------------------|
| 6 | `testCrearTareaNulo` | Verifica que no se permite crear una tarea sin título            |

**Clase bajo prueba**: `ServicioTareas`  
**Dependencia simulada (con Mockito)**: `RepositorioTareas`

### 🧪 Pruebas unitarias con Mockito

| # | Nombre del test                                          | Propósito                                                             |
|---|----------------------------------------------------------|----------------------------------------------------------------------|
| 7 | `testCrearTarea`                                         | Verifica que se llama al método `guardar()` del repositorio          |
| 8 | `testMarcarCompletada` | Verifica que se llama al método `guardar()` con la tarea marcada como completada |

---
## 🔁 Refactorización a Vert.x

En esta etapa, debes refactorizar la arquitectura imperativa del sistema para implementar una versión **reactiva** basada en **Vert.x** y el patrón **Publish/Subscribe** utilizando el **EventBus** de Vert.x.

### 🎯 Objetivo
Separar las responsabilidades del sistema en distintos **Verticles**, que se comunican exclusivamente mediante mensajes asincrónicos, sin llamadas directas entre clases.
Implementar un `Verticle` llamado `RepositorioTareasVerticle` que simule el almacenamiento de tareas en una lista (`List<JsonObject>`), permitiendo:

---

### 🧩 Comportamiento esperado

1. **Toda la comunicación entre componentes debe hacerse a través del `EventBus`** de Vert.x, usando canales identificados por cadenas como `"tareas.crear"` o `"tareas.completadas"`.

2. Cada componente del sistema debe implementarse como un **Verticle** independiente, con responsabilidad clara:
    - `RepositorioTareaVerticle`: maneja el acceso a la base de datos simulada.
    - `ServicioTareaVerticle`: orquesta la lógica del negocio (recibe eventos, consulta al repositorio, publica resultados).
    - `ClienteVerticle`: actúa como interfaz del usuario (p. ej., simulación por consola o pruebas automáticas), enviando mensajes como "crear tarea" o "listar tareas".

3. **No debe haber llamadas directas entre clases**, ni uso compartido de instancias (`new ServicioTareas(...)`, etc.).  
   Todo debe funcionar por **mensajes JSON** (usando `JsonObject`) publicados y consumidos en el EventBus.

4. Las operaciones deben mantener el mismo comportamiento funcional que la versión imperativa:
    - Crear una tarea
    - Listar tareas pendientes o completadas

5. Debes definir claramente los **canales de comunicación** del EventBus y el **formato de los mensajes** (entrada y salida), por ejemplo:

   | Canal (`address`)       | Propósito                    | Entrada                   | Respuesta esperada            |
   |-------------------------|------------------------------|---------------------------|-------------------------------|
   | `tareas.crear`          | Crear una nueva tarea        | `{ "titulo": "...", ...}` | `{ "estado": "ok", "id": 1 }` |
   | `tareas.completar`      | Marcar como completada       | `{ "id": 1 }`             | `{ "estado": "ok" }`          |
   | `tareas.listarPendientes` | Listar tareas no completadas | `{}`                      | `[ { "id": ..., "titulo": ... }, ... ]` |

6. Comportamiento del `RepositorioTareasVerticle`

   - El `RepositorioTareasVerticle` debe:
       - Mantener una lista interna (`List<JsonObject>`)
       - Escuchar mensajes en canales del EventBus:
           - `"tareas.crear"` → agrega una tarea a la lista
           - `"tareas.listar"` → responde con todas las tareas almacenadas

   - La **única forma de interactuar con la lista es a través del EventBus**.

   - Cada tarea se representa como un `JsonObject` con al menos:
     ```json
     {
       "id": 1,
       "titulo": "Estudiar para la prueba",
       "completada": false
     }
     ```
---



> ⚠️ Recuerda que **no debe existir ninguna llamada directa entre clases** como `new ServicioTareas(...)`. Todo debe pasar por el EventBus.


