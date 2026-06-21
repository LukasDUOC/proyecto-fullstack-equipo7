# Proyecto Semestral — Equipo 7

## Microservicios y repositorios

| Servicio       | Encargado | Repositorio                                                                                |
| :------------- | :-------- | :----------------------------------------------------------------------------------------- |
| Usuario-Servic | lukas     | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/usuario-service          |
| Contenido-Serv | franco    | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/contenido-service        |
| Ratings-Serv   | Nicolas   | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/ratings-service          |
| Reseña-Servic  | Nicolas   | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/rese%C3%B1as-service     |
| Favorito-Servi | Lukas     | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/favorito-service         |
| Recomendacion- | Lukas     | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/recomendaciones-service  |
| Lista-Service  | Franco    | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/lista-service            |
| Filtro-Service | Nicolas   | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/filtros-service          |
| Tendencia-Serv | Franco    | https://github.com/LukasDUOC/proyecto-fullstack-equipo7/tree/main/tendencia-service        |

## Links de Swaggers

| Recurso | URL |
|---------|-----|
| Swagger Contenido | `http://54.208.91.193:8081/doc/swagger-ui/index.html` |
| Swagger Busqueda | `http://54.208.91.193:8086/doc/swagger-ui/index.html` |
| Swagger Lista | `http://54.208.91.193:8089/doc/swagger-ui/index.html` |
| Swagger Tendencia | `http://54.208.91.193:8088/doc/swagger-ui/index.html` |

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
| 5. Contrato HTTP | ⚠️ Parcial | Listados masivos, consultas por ID, creación (201), rechazo por campos vacíos (400) y flujos felices de edición (PUT) y eliminación (DELETE). | Casos de error (404) para los endpoints de actualización (PUT) y eliminación (DELETE). |



### Reflexión y Deuda Técnica

- **Riesgo identificado**: La API Contenido se encuentra 100% operativo, estable y funcional en todas sus capas CRUD, pero al tener algunos métodos, endpoints de actualización (`PUT`) y de eliminación (`DELETE`) que operan de manera correcta pero que no han sido incluidos en los tests. Como faltan algunos casos de prueba para los flujos de error, si en el futuro modificamos cómo se transforman los datos o las reglas que validan los campos, la API podría empezar a fallar de forma oculta sin que los tests actuales lo detecten.
  
-**Acción futura**: Agregar los tests que faltan para las búsquedas por palabras, la actualización de datos y los casos de error al modificar o eliminar contenidos.

