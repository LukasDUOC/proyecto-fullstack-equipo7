# Proyecto Semestral — Equipo 7

## Microservicios y repositorios

| Servicio       | Encargado | Repositorio                                                                                |
| :------------- | :-------- | :----------------------------------------------------------------------------------------- |
| Usuario-Service | lukas     | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/usuario-service          |
| Contenido-Service | franco    | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/contenido-service        |
| Ratings-Service  | Nicolas   | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/ratings-service          |
| Reseña-Service  | Nicolas   | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/rese%C3%B1as-service     |
| Favorito-Service | Lukas     | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/favorito-service         |
| Recomendacion-Service | Lukas     | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/recomendaciones-service  |
| Lista-Service  | Franco    | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/lista-service            |
| Filtro-Service | Nicolas   | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/filtros-service          |
| Tendencia-Service | Franco    | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/tendencia-service        |
| Busqueda-Service | Franco    | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/busqueda-service         |

## Links de Swaggers

| Recurso | URL |
|---------|-----|
| Swagger Contenido | `http://54.208.91.193:8081/doc/swagger-ui/index.html` |
| Swagger Busqueda | `http://54.208.91.193:8086/doc/swagger-ui/index.html` |
| Swagger Lista | `http://54.208.91.193:8089/doc/swagger-ui/index.html` |
| Swagger Tendencia | `http://54.208.91.193:8088/doc/swagger-ui/index.html` |
| Swagger Usuario | `http://54.208.91.193:8090/doc/swagger-ui/index.html` |
| Swagger Favoritos | `http://54.208.91.193:8084/doc/swagger-ui/index.html` |
| Swagger Recomendaciones | `http://54.208.91.193:8085/doc/swagger-ui/index.html` |
| Swagger Ratings | `http://54.208.91.193:8082/doc/swagger-ui/index.html` |
| Swagger Reseña | `http://54.208.91.193:8083/doc/swagger-ui/index.html` |
| Swagger Filtro | `http://54.208.91.193:8087/doc/swagger-ui/index.html` |

## Plan de Pruebas Unitarias — API Contenidos

### Reglas de Negocio Críticas

1. **Integridad del Modelo (Model)**: Toda instancia de `Contenido` debe resguardar la consistencia de sus atributos en constructores y mutadores, asegurando la correspondencia lógica en métodos de igualdad y texto (`hashCode` y `toString`).
2. **Ciclo de Persistencia Segura (Repository)**: La base de datos debe autogenerar IDs incrementales al registrar nuevos elementos y responder de forma controlada con contenedores vacíos (`Optional.empty()`) ante consultas de identificadores inexistentes.
3. **Validación de Unicidad (Service)**: No se puede registrar un nuevo contenido si la función `existsByTituloIgnoreCase` detecta que el título exacto ya se encuentra ingresado en el sistema.
4. **Control de Existencia (Service)**: Cualquier operación de consulta individual (`findById`), edición (`PUT`) o eliminación (`DELETE`) sobre un identificador que no existe en el sistema debe abortar de inmediato lanzando la excepción `RecursoNoEncontradoException`.
5. **Validación del Contrato HTTP (Controller)**: Los endpoints expuestos por la API deben responder con códigos semánticos estándar (200 OK, 201 Created, 204 No Content). Las solicitudes que violen las restricciones de validación (como enviar un título vacío) deben ser rechazadas con un error 400 Bad Request.
6. **Búsqueda Dinámica por Coincidencias (Repository/Service/Controller)**: El sistema debe permitir la consulta de elementos filtrando por cadenas parciales en el título mediante la función `findByTituloContainingIgnoreCase`, ignorando mayúsculas y minúsculas.

### Cobertura Actual Contenido Test.

| Regla | Estado | Casos Cubiertos | Pendiente |
|-------|--------|-----------------|-----------|
| 1. Integridad del Modelo | ✅ Cubierta | Constructor vacío, constructor completo, Getters/Setters, verificación de Hash y formato `toString`. | — |
| 2. Ciclo de Persistencia | ✅ Cubierta | Guardado con ID autoincremental, listado total, búsqueda feliz por ID, ID inexistente y borrado exitoso en BD. | — |
| 3. Validación de Unicidad | ✅ Cubierta | Creación exitosa en el servicio (caso feliz) y bloqueo con excepción por título duplicado (caso error). | — |
| 4. Control de Existencia | ⚠️ Parcial | Búsqueda por ID existente (caso feliz) y manejo de excepción si el ID no existe en el sistema (caso error). | Falta ID inexistente para los métodos de actualización (`actualizar`) y eliminación (`eliminar`) en el servicio. |
| 5. Metodos HTTP | ⚠️ Parcial | Listados masivos, consultas por ID, creación (201), rechazo por campos vacíos (400) y flujos felices de edición (PUT) y eliminación (DELETE). | Casos de error (404) para los endpoints de actualización (PUT) y eliminación (DELETE). |



### Reflexión y Deuda Técnica

- **Riesgo identificado**: Riesgo identificado: La API funciona al 100%, pero como faltan algunos tests para los flujos de error al modificar o eliminar, cualquier cambio futuro en las reglas o datos podría hacer que el sistema falle de forma oculta sin que las pruebas actuales lo detecten.
  
- **Acción futura**: Agregar los tests que faltan para las búsquedas por palabras, la actualización de datos y los casos de error al modificar o eliminar contenidos.



## Plan de Pruebas Unitarias — API Búsqueda

### Reglas de Negocio Críticas

1. **Integridad del Modelo (Model)**: Toda instancia del modelo `Busqueda` debe resguardar la consistencia de sus atributos esenciales (`id`, `contenidoId` y `titulo`), validando la equivalencia lógica y sus formatos en métodos de igualdad y texto (`hashCode` y `toString`).
2. **Ciclo de Persistencia Segura (Repository)**: El motor de base de datos debe autogenerar IDs incrementales al guardar registros y permitir las operaciones CRUD básicas sin alterar la información original.
3. **Integración con Clientes Feign (Service)**: El servicio debe consultar de manera inteligente a la API externa de contenidos usando el ID o el Título, capturando las respuestas exitosas para retornar los datos correspondientes y guardarlos automáticamente en el historial.
4. **Control de Existencia (Service)**: Cualquier consulta individual o acción de eliminación en el historial de búsquedas que use un ID inválido o que no esté registrado debe abortar de inmediato lanzando la excepción `RecursoNoEncontradoException`.
5. **Validación del Contrato HTTP (Controller)**: Los endpoints expuestos por la API deben responder con códigos semánticos estándar (200 OK para listados/búsquedas y 204 No Content para eliminaciones de historial), gestionando de manera correcta el formato JSON de las solicitudes.

### Cobertura Actual

| Regla | Estado | Casos Cubiertos | Pendiente |
|-------|--------|-----------------|-----------|
| 1. Integridad del Modelo | ✅ Cubierta | Constructor vacío, completo, Getters/Setters, validación del método Equals/HashCode y formato de texto `toString`. | — |
| 2. Ciclo de Persistencia | ✅ Cubierta | Guardar con ID autoincremental, listar todas las búsquedas, encontrar por ID existente y eliminar registros en base de datos. | — |
| 3. Integración Feign | ✅ Cubierta | Búsqueda feliz por ID y búsqueda feliz por Título, incluyendo la inyección y persistencia automática en el repositorio de historial. | — |
| 4. Control de Existencia | ⚠️ Parcial | Obtención del historial (completo o vacío), consulta por ID existente y error de consulta por ID inexistente (404). | Falta Error para la eliminación de IDs inexistentes, borrado masivo (`eliminarTodo`), excepciones de comunicación (errores 503) y búsquedas parciales por texto. |
| 5. Metodos HTTP | ⚠️ Parcial | Peticiones POST para búsquedas (por ID y por título) con código 200, listado de historial completo (200), consulta por ID (200) y caso feliz de eliminación por ID (204). | Falta los escenarios de error (404), la búsqueda por coincidencia de términos de texto y el endpoint de borrado masivo (`/historial/limpiar`)|

### Reflexión y Deuda Técnica

- **Riesgo identificado**: La API funciona al 100%, pero como faltan algunos tests para los flujos de error al eliminar, cualquier cambio futuro en las reglas o datos podría hacer que el sistema falle de forma oculta sin que las pruebas actuales lo detecten.
- **Acción futura**: Agregar los tests que faltan para las búsquedas por palabras, los casos de error al eliminar contenidos.

## Plan de Pruebas Unitarias — API Usuarios

### Reglas de Negocio Críticas

1. **Integridad del Modelo (Model)**: Toda instancia de `Usuario` debe resguardar la consistencia de sus atributos en constructores y mutadores, asegurando la correspondencia lógica en métodos de igualdad y texto (`hashCode` y `toString`).
2. **Ciclo de Persistencia Segura (Repository)**: La base de datos debe autogenerar IDs incrementales al registrar nuevos elementos y responder de forma controlada con contenedores vacíos (`Optional.empty()`) o booleanos ante consultas de identificadores o emails inexistentes.
3. **Validación de Unicidad (Service)**: No se puede registrar un nuevo usuario si la validación detecta que el correo electrónico (`email`) exacto ya se encuentra ingresado en el sistema.
4. **Control de Existencia (Service)**: Cualquier operación de consulta individual (`findById`, `findByEmail`) o eliminación (`eliminarPorEmail`) sobre un identificador que no existe en el sistema debe abortar de inmediato lanzando la excepción `RecursoNoEncontradoException`.
5. **Validación del Contrato HTTP (Controller)**: Los endpoints expuestos por la API deben responder con códigos semánticos estándar (200 OK, 201 Created, 204 No Content). Las solicitudes que violen las restricciones de validación (como enviar un email inválido o nombre vacío) deben ser rechazadas con un error 400 Bad Request.
6. **Gestión Identificada por Email (Repository/Service/Controller)**: El sistema debe permitir la consulta y eliminación de elementos específicos utilizando el correo electrónico (`email`) mediante funciones como `findByEmail` y `deleteByEmail`.

### Cobertura Actual Usuario Test.

| Regla | Estado | Casos Cubiertos | Pendiente |
|-------|--------|-----------------|-----------|
| 1. Integridad del Modelo | ✅ Cubierta | Constructor vacío, constructor completo, Getters/Setters, verificación de Hash y formato `toString`. | — |
| 2. Ciclo de Persistencia | ✅ Cubierta | Guardado con ID autoincremental, comprobación de existencia por email, búsqueda feliz por email, retorno vacío por email inexistente y borrado exitoso en BD. | — |
| 3. Validación de Unicidad | ⚠️ Parcial | El repositorio detecta correos existentes (`existsByEmail`). | Falta bloqueo con excepción por email duplicado (caso de error) en la creación desde el servicio. |
| 4. Control de Existencia | ⚠️ Parcial | Búsqueda por ID existente (caso feliz) y manejo de excepción si el ID no existe en el sistema (caso error). | Falta email inexistente para el método de búsqueda y eliminación (`eliminarPorEmail`) en el servicio. |
| 5. Metodos HTTP | ⚠️ Parcial | Listados masivos, consultas por ID (404) y Email (200), creación (201), rechazo por campos inválidos (400) y flujos felices de eliminación (DELETE). | Casos de error (404) para eliminación de email inexistente y faltan **todos** los flujos de actualización (PUT). |

### Reflexión y Deuda Técnica

- **Riesgo identificado**: Riesgo identificado: La API funciona correctamente en sus flujos base, pero como faltan tests para la validación de correos duplicados al crear y todos los flujos de error/actualización (PUT), cualquier cambio futuro podría generar duplicidad de usuarios o que el sistema falle de forma oculta sin que las pruebas lo detecten.
  
- **Acción futura**: Agregar los tests que faltan para el bloqueo por email duplicado, la actualización completa de datos (PUT) y los casos de error al modificar o eliminar usuarios que no existen.


## Plan de Pruebas Unitarias — API Favoritos

### Reglas de Negocio Críticas

1. **Integridad del Modelo (Model)**: Toda instancia de `Favorito` debe resguardar la consistencia de sus atributos en constructores y mutadores, asegurando la correspondencia lógica en métodos de igualdad y texto (`hashCode` y `toString`).
2. **Ciclo de Persistencia Segura (Repository)**: La base de datos debe autogenerar IDs incrementales al registrar nuevos elementos y responder de forma controlada con contenedores vacíos (`Optional.empty()`) ante consultas de identificadores inexistentes.
3. **Validación de Relaciones Externas (Service)**: Al crear un favorito, el sistema debe interactuar con clientes externos (`UsuarioClient` y `ContenidoClient`) para gestionar la relación entre un usuario y un contenido.
4. **Control de Existencia (Service)**: Cualquier operación de consulta individual (`findById`) o de modificación de estado (`actualizarEstado`) sobre un identificador que no existe en el sistema debe abortar de inmediato lanzando la excepción `RecursoNoEncontradoException`.
5. **Validación del Contrato HTTP (Controller)**: Los endpoints expuestos por la API deben responder con códigos semánticos estándar (200 OK, 201 Created). Los flujos de error por recursos no encontrados deben retornar 404 Not Found gestionados por su handler.
6. **Actualización Parcial de Estado (Service/Controller)**: El sistema debe permitir modificar exclusivamente el estado de favorito (booleano) a través del verbo HTTP `PATCH`, asegurando la inmutabilidad de los identificadores de usuario y contenido.

### Cobertura Actual Favorito Test.

| Regla | Estado | Casos Cubiertos | Pendiente |
|-------|--------|-----------------|-----------|
| 1. Integridad del Modelo | ✅ Cubierta | Constructor vacío, constructor completo, Getters/Setters, verificación de Hash y formato `toString`. | — |
| 2. Ciclo de Persistencia | ✅ Cubierta | Guardado con ID autoincremental, listado total, búsqueda feliz por ID, ID inexistente y borrado exitoso en BD. | — |
| 3. Validación de Relaciones | ⚠️ Parcial | Creación exitosa probada en el servicio simulando llamadas a los clientes (`UsuarioClient` y `ContenidoClient`). | Faltan flujos de error simulando que el usuario o el contenido no existen en los otros microservicios. |
| 4. Control de Existencia | ⚠️ Parcial | Búsqueda por ID y actualización de estado (PATCH) con casos felices, además del manejo de excepción si el ID no existe en el sistema (caso error). | Falta el método y prueba de eliminación (`eliminar`) en el servicio para IDs inexistentes. |
| 5. Metodos HTTP | ⚠️ Parcial | Listados masivos, creación (201), actualización de estado (PATCH 200) y rechazo por ID no encontrado (404 en GET). | Casos de éxito (200) en consulta por ID individual, rechazo por campos vacíos (400 Bad Request), casos 404 para el PATCH, y todos los tests para eliminación (DELETE). |

### Reflexión y Deuda Técnica

- **Riesgo identificado**: Riesgo identificado: La API funciona correctamente para lecturas y actualizaciones parciales, pero al mockear respuestas nulas en los clientes externos durante la creación, se ocultan posibles fallos de integración. Además, al faltar las pruebas de eliminación (DELETE) y validaciones de formato (400 Bad Request), la API podría fallar silenciosamente o aceptar datos corruptos.
  
- **Acción futura**: Agregar los tests que faltan para validaciones de DTO (Bad Request), simular fallos controlados en los clientes externos (`ContenidoClient` y `UsuarioClient`) y construir la cobertura completa para el flujo de eliminación (DELETE) en capas de Servicio y Controlador.

