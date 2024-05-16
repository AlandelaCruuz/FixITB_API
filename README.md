# Fix_ITB API


## Endpoints de Usuarios

### Iniciar sesión de usuario
- **Método:** POST
- **Ruta:** `/login`
- **Descripción:** Inicia sesión para un usuario utilizando un token.
- **Parámetros de la solicitud:** 
  - `Tokn`: Detalles del token del usuario.
- **Respuesta:**
  - Devuelve un objeto `LoginResponse` con el token generado y los detalles del usuario verificado.
  - Código de respuesta:
    - `200 OK` si las credenciales son válidas.
    - `203 Non-Authoritative Information` si solo se permiten correos electrónicos de ITB.

### Obtener todos los usuarios
- **Método:** GET
- **Ruta:** `/users`
- **Descripción:** Devuelve una lista de todos los usuarios registrados en el sistema.
- **Respuesta:**
  - Devuelve una lista de usuarios.
  - Código de respuesta: `200 OK`

### Asignar rol a un usuario
- **Método:** PUT
- **Ruta:** `/users/{userId}/role/{newRole}`
- **Descripción:** Asigna un nuevo rol a un usuario específico.
- **Parámetros de URL:**
  - `userId` (int): ID del usuario.
  - `newRole` (string): Nuevo rol a asignar.
- **Respuesta:**
  - Devuelve los detalles del usuario actualizado.
  - Código de respuesta:
    - `200 OK` si la operación es exitosa.
    - `404 Not Found` si el usuario no se encuentra.

### Obtener usuario por email
- **Método:** GET
- **Ruta:** `/users/{email}`
- **Descripción:** Devuelve los detalles de un usuario específico basado en su email.
- **Parámetros de URL:**
  - `email` (string): Email del usuario.
- **Respuesta:**
  - Devuelve los detalles del usuario correspondiente al email proporcionado.
  - Código de respuesta:
    - `200 OK` si el usuario es encontrado.
    - `400 Bad Request` si falta el parámetro `email`.
    - `401 Unauthorized` si no se proporciona el token.
    - `404 Not Found` si el usuario no es encontrado.

### Eliminar un usuario
- **Método:** DELETE
- **Ruta:** `/users/delete/{userId}`
- **Descripción:** Elimina un usuario específico del sistema.
- **Parámetros de URL:**
  - `userId` (int): ID del usuario que se desea eliminar.
- **Respuesta:**
  - Devuelve un mensaje indicando si la eliminación fue exitosa.
  - Código de respuesta:
    - `200 OK` si la eliminación es exitosa.
    - `400 Bad Request` si el `userId` es inválido.
    - `500 Internal Server Error` si hay un fallo en la eliminación del usuario.



## Endpoints de Incidencias

### Obtener todas las incidencias
- **Método:** GET
- **Ruta:** `/incidences`
- **Descripción:** Devuelve una lista de todas las incidencias registradas en el sistema.
- **Respuesta:**
  - Devuelve una lista de incidencias.
  - Código de respuesta: `200 OK`

### Crear una nueva incidencia
- **Método:** POST
- **Ruta:** `/incidences`
- **Descripción:** Registra una nueva incidencia en el sistema.
- **Parámetros de la solicitud:**
  - `Incidence`: Detalles de la incidencia a registrar.
- **Respuesta:**
  - Devuelve los detalles de la incidencia recién creada.
  - Código de respuesta: `200 OK`

### Obtener incidencias por ID de usuario
- **Método:** GET
- **Ruta:** `/incidences/{userId}`
- **Descripción:** Devuelve una lista de incidencias asociadas con un usuario específico.
- **Parámetros de URL:**
  - `userId` (int): ID del usuario.
- **Respuesta:**
  - Devuelve una lista de incidencias para el usuario especificado.
  - Código de respuesta:
    - `200 OK` si se encuentran incidencias.
    - `400 Bad Request` si falta el parámetro `userId`.

### Obtener incidencia por ID
- **Método:** GET
- **Ruta:** `/incidences/incidence/{incidenceId}`
- **Descripción:** Devuelve los detalles de una incidencia específica.
- **Parámetros de URL:**
  - `incidenceId` (int): ID de la incidencia.
- **Respuesta:**
  - Devuelve los detalles de la incidencia correspondiente al ID proporcionado.
  - Código de respuesta:
    - `200 OK` si se encuentra la incidencia.
    - `400 Bad Request` si el `incidenceId` es inválido.
    - `404 Not Found` si la incidencia no es encontrada.

### Actualizar una incidencia
- **Método:** PUT
- **Ruta:** `/incidences/{incidenceId}`
- **Descripción:** Actualiza los detalles de una incidencia específica.
- **Parámetros de URL:**
  - `incidenceId` (int): ID de la incidencia.
- **Parámetros de la solicitud:**
  - `Incidence`: Detalles de la incidencia a actualizar.
- **Respuesta:**
  - Devuelve un mensaje indicando si la actualización fue exitosa.
  - Código de respuesta:
    - `200 OK` si la actualización es exitosa.
    - `400 Bad Request` si el `incidenceId` es inválido.
    - `500 Internal Server Error` si hay un fallo en la actualización de la incidencia.

### Eliminar una incidencia
- **Método:** DELETE
- **Ruta:** `/incidences/delete/{incidenceId}`
- **Descripción:** Elimina una incidencia específica del sistema.
- **Parámetros de URL:**
  - `incidenceId` (int): ID de la incidencia que se desea eliminar.
- **Respuesta:**
  - Devuelve un mensaje indicando si la eliminación fue exitosa.
  - Código de respuesta:
    - `200 OK` si la eliminación es exitosa.
    - `400 Bad Request` si el `incidenceId` es inválido.
    - `500 Internal Server Error` si hay un fallo en la eliminación de la incidencia.

### Obtener incidencias por técnico asignado
- **Método:** GET
- **Ruta:** `/incidences/tecnic/{email}`
- **Descripción:** Devuelve una lista de incidencias asignadas a un técnico específico.
- **Parámetros de URL:**
  - `email` (string): Email del técnico.
- **Respuesta:**
  - Devuelve una lista de incidencias para el técnico especificado.
  - Código de respuesta:
    - `200 OK` si se encuentran incidencias.
    - `400 Bad Request` si falta el parámetro `email`.

