# SIPV - Sistema de Inventario y Punto de Venta (Backend)

![Java](https://img.shields.io/badge/Java%2025-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%204.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

Backend de una tienda de comics y mangas. API REST con arquitectura limpia por capas
y persistencia en MongoDB. La documentacion de los requisitos y los diagramas estan en
la [wiki del repositorio](https://github.com/Jenifrutica/SIPV/wiki).

## Alcance

Este entregable corresponde **unicamente al backend** (API REST). No se incluyen
wireframes ni diseno de interfaz grafica porque el proyecto no contempla capa de
presentacion: toda la interaccion se realiza y se verifica a traves de los endpoints
REST, documentados y probados con Swagger UI. El consumo desde un cliente grafico queda
fuera del alcance de esta entrega.

## Stack

- Java 25 (LTS)
- Spring Boot 4.0 (Spring Web, Spring Data MongoDB, Validation)
- MongoDB
- Swagger UI (springdoc) para documentar y probar la API
- Maven

## Estructura por capas

```
com.tienda.sipv
  controller   -> endpoints REST (Autenticacion, Inventario, Venta)
  service      -> logica de negocio (interfaces) + service.impl
  repository   -> acceso a datos (MongoRepository)
  dto          -> objetos de entrada y salida
  model        -> entidades de dominio + model.enums
  exception    -> excepciones de negocio + manejador global
  config       -> configuracion de Swagger
```

## Modulos

- **Autenticacion:** login, token y gestion de usuarios (roles Administrador / Empleado).
- **Inventario:** catalogo (obras), recepcion, estados del ejemplar y disponibilidad.
- **Ventas:** ventas, devoluciones, anulacion y clientes.

## Como ejecutar

1. Configurar la conexion a MongoDB en `application.properties` mediante la propiedad
   `spring.mongodb.uri` (prefijo de Spring Boot 4.0). Admite una instancia local
   (`mongodb://localhost:27017/sipv`) o un cluster de MongoDB Atlas (`mongodb+srv://...`).
2. Ejecutar:

   ```
   mvn spring-boot:run
   ```

3. Abrir la documentacion interactiva en:

   ```
   http://localhost:8080/swagger-ui.html
   ```

## Endpoints principales

| Metodo | Ruta | Descripcion |
|---|---|---|
| POST | `/auth/login` | Iniciar sesion (devuelve token y rol) |
| POST | `/auth/usuarios/empleado` | Crear empleado (admin) |
| POST | `/auth/usuarios/administrador` | Crear administrador (admin) |
| GET | `/auth/usuarios` | Listar usuarios (admin) |
| POST | `/obras` | Registrar obra |
| GET | `/obras?titulo=` | Consultar y buscar obras |
| GET | `/obras/disponibilidad?titulo=` | Consultar disponibilidad (publico) |
| POST | `/recepciones` | Registrar recepcion de un lote |
| PATCH | `/ejemplares/{sku}/estado?nuevoEstado=` | Cambiar estado de un ejemplar |
| DELETE | `/ejemplares/{sku}` | Dar de baja un ejemplar defectuoso (admin) |
| POST | `/ventas` | Registrar venta y emitir recibo |
| POST | `/ventas/{id}/anular` | Anular una venta (admin) |
| GET | `/recibos` y `/recibos/{id}` | Consultar recibos |
| POST | `/devoluciones` | Registrar devolucion |
| POST | `/clientes` | Registrar cliente |
| GET | `/clientes` y `/clientes/{id}` | Consultar clientes |
| PUT | `/clientes/{id}` | Editar cliente |

Las operaciones sensibles (crear usuarios, anular venta, dar de baja) requieren la
cabecera `X-Rol: ADMINISTRADOR`. Es una simplificacion del ejercicio; en un sistema
real el rol se obtendria del token.
