# Examen Práctico - Arquitectura de Software (Parcial II)

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-green)
![MongoDB](https://img.shields.io/badge/MongoDB-Database-forestgreen)

API REST desarrollada para la gestión de sucursales bancarias (Branch) y sus respectivos feriados, implementando una arquitectura en capas y persistencia no relacional.

## 📋 Datos del Estudiante

| Campo | Detalle |
| :--- | :--- |
| **Nombre** | **Ian Alvarez Cordova** |
| **Asignatura** | Arquitectura de Software |
| **Universidad** | Universidad de las Fuerzas Armadas ESPE |
| **Fecha** | Diciembre 2025 |

---

## 🚀 Descripción del Proyecto

El sistema permite realizar operaciones CRUD sobre entidades bancarias llamadas **Sucursales**. Cada sucursal tiene un ciclo de vida, datos de auditoría y una lista anidada de feriados.

### Características Principales
* **Persistencia NoSQL:** Uso de MongoDB para almacenar documentos con estructuras anidadas (arrays de feriados).
* **Arquitectura Limpia:** Separación de responsabilidades en Controladores, Servicios y Repositorios.
* **Manejo de Errores:** `GlobalExceptionHandler` para respuestas HTTP coherentes (404, 400, 500).
* **Documentación Viva:** Integración con Swagger UI / OpenAPI.
* **Logs:** Trazabilidad de operaciones mediante SLF4J.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 21 (JDK 21)
* **Framework:** Spring Boot 3.2.5
* **Base de Datos:** MongoDB
* **Gestor de Dependencias:** Maven
* **Documentación API:** SpringDoc OpenApi (Swagger)
* **Utilidades:** Lombok

---

## ⚙️ Configuración e Instalación

### 1. Prerrequisitos
* Tener instalado **Java JDK 21**.
* Tener instalado **MongoDB** (y el servicio corriendo en el puerto 27017).
* Tener instalado **Maven**.

### 2. Clonar el Repositorio
```bash
git clone [https://github.com/IanAlvarezCordova/ExamenII_Parcial_Arquitectura.git](https://github.com/IanAlvarezCordova/ExamenII_Parcial_Arquitectura.git)
cd ExamenII_Parcial_Arquitectura
```
### 3. Configuración de Base de Datos
La aplicación está configurada para conectarse a una instancia local de MongoDB. Verifica el archivo `src/main/resources/application.properties` para asegurar la conexión:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/banquito_examen
server.port=8081
```

*Nota: No es necesario crear la base de datos `banquito_examen` manualmente; el aplicativo la generará automáticamente al insertar el primer registro.*

### 4. Ejecutar el Proyecto
Una vez clonado y configurado, ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn spring-boot:run
```
---

## 📖 Documentación y Pruebas (Swagger UI)

El proyecto implementa **OpenAPI (Swagger)** para documentar y probar los servicios REST de manera interactiva.

👉 **URL de Acceso:** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

### Tabla de Endpoints Disponibles

| Método HTTP | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/api/sucursales` | Obtener el listado de todas las sucursales. |
| **POST** | `/api/sucursales` | Crear una nueva sucursal (sin feriados iniciales). |
| **GET** | `/api/sucursales/{id}` | Buscar una sucursal por su ID único. |
| **PUT** | `/api/sucursales/{id}/telefono` | Modificar únicamente el teléfono (actualiza auditoría). |
| **POST** | `/api/sucursales/{id}/feriados` | Agregar un nuevo feriado a la lista de la sucursal. |
| **DELETE**| `/api/sucursales/{id}/feriados/{fecha}` | Eliminar un feriado específico por su fecha. |
| **GET** | `/api/sucursales/{id}/feriados` | Listar todos los feriados de una sucursal. |
| **GET** | `/api/sucursales/{id}/es-feriado/{fecha}` | Verificar si una fecha dada es feriado (True/False). |

---

## 📂 Estructura del Proyecto

El código fuente sigue una arquitectura en capas para asegurar la mantenibilidad y escalabilidad:

```text
com.ecusol.core
├── controller   # Capa de presentación (Manejo de Peticiones HTTP)
│   └── SucursalController.java
├── service      # Lógica de Negocio y Validaciones
│   └── SucursalService.java
├── repository   # Acceso a Datos (MongoDB)
│   └── SucursalRepository.java
├── model        # Entidades del Dominio (Documentos)
│   ├── Sucursal.java
│   └── Feriado.java
├── dto          # Objetos de Transferencia de Datos
│   ├── SucursalRequestDTO.java
│   └── FeriadoDTO.java
└── exception    # Manejo Global de Errores
    └── GlobalExceptionHandler.java
```
---

## 🧪 Ejemplos de Prueba (JSON)

Para facilitar la evaluación, puede utilizar los siguientes datos de prueba directamente en Swagger UI.

### 1. Crear Sucursal (POST)
**Endpoint:** `/api/sucursales`
```json
{
  "name": "Agencia Matriz Amazonas",
  "emailAddress": "matriz@banquito.fin.ec",
  "phoneNumber": "022999999"
}
```
### 2. Agregar Feriado (POST)
**Endpoint:** `/api/sucursales/{id}/feriados`
*(Requiere el ID generado en el paso anterior)*
```json
{
  "date": "2025-12-06",
  "name": "Fundación de Quito"
}
```

### 3. Modificar Teléfono (PUT)
**Endpoint:** `/api/sucursales/{id}/telefono`
```json
{
  "phoneNumber": "0998765432"
}
```
``

---

## 📊 Visualización de Logs

El sistema cumple con el requerimiento de **Logging** utilizando SLF4J.
Los logs de información (`INFO`) y error (`ERROR`) se visualizan en **tiempo real en la consola de ejecución** (Standard Output) del IDE o servidor al realizar las peticiones.

**Ejemplo de salida en consola:**
```text
INFO ... : REST: Crear sucursal SucursalRequestDTO(name=Agencia Matriz Amazonas...)
INFO ... : Iniciando creación de sucursal...
INFO ... : Sucursal creada con ID: 65a4s5d...

