# VALORIA — Contrato de API (18 endpoints)

> Fuente completa: [`VALORIA_contrato_API.pdf`](VALORIA_contrato_API.pdf). Este archivo es un
> resumen navegable en Markdown. El PDF menciona un `openapi.yaml` como versión formal en
> OpenAPI 3.0 — **aún no lo tenemos en el repo**; hay que generarlo (a mano o con
> springdoc-openapi a partir del código) antes o durante la Fase 4.

## Convenciones

- Recursos en **plural**: `/misiones`, no `/mision`.
- El verbo de la acción va en el **método HTTP**, nunca en la URL.
- Acciones que no son CRUD puro se modelan como sub-recurso: `POST /misiones/{id}/completar`.
- Todos los endpoints requieren JWT (`Authorization: Bearer <token>`) excepto los marcados
  como públicos (No en la columna Auth).

## Ejemplo de endpoint documentado

```
POST /misiones/{id}/completar
Descripción: Marca una misión como completada y otorga su recompensa
Autenticación: requerida (JWT)

Respuesta 200 OK:
{
  "xpGanado": 25,
  "oroGanado": 10,
  "rachaActual": 4,
  "subioDeNivel": false
}
Respuesta 400: la misión ya fue completada en este ciclo
Respuesta 404: la misión no existe o no pertenece al usuario
```

## Listado completo

### EP-01 · Cuenta y autenticación

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| POST | `/auth/registro` | Crea cuenta con correo/contraseña | No | HU-01 |
| POST | `/auth/login` | Inicia sesión, devuelve JWT | No | HU-02 |
| POST | `/auth/google` | Login/registro con Google | No | HU-04 |
| POST | `/auth/logout` | Invalida la sesión actual | Sí | HU-03 |

### EP-02 · Personaje

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| POST | `/personajes` | Crea el personaje (nombre + clase) | Sí | HU-05 |
| GET | `/personajes/me` | Devuelve la hoja de personaje | Sí | HU-07 |
| PUT | `/personajes/me` | Edita nombre/avatar | Sí | HU-06 |

### EP-03 · Misiones

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| POST | `/misiones` | Crea una misión nueva | Sí | HU-08 |
| GET | `/misiones` | Lista misiones activas | Sí | HU-09 |
| PUT | `/misiones/{id}` | Edita una misión (incluye `metaId` para vincularla) | Sí | HU-10, HU-17 |
| DELETE | `/misiones/{id}` | Elimina una misión | Sí | HU-10 |
| POST | `/misiones/{id}/completar` | Completa y otorga recompensa | Sí | HU-11, HU-12 |

### EP-04 · Racha

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| POST | `/personajes/me/pociones/usar` | Usa una poción para proteger la racha | Sí | HU-15 |

> La racha en sí no tiene endpoint propio: viaja dentro de la respuesta de `/misiones` y
> `/personajes/me`.

### EP-05 · Jefes (metas)

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| POST | `/metas` | Crea un jefe/meta grande | Sí | HU-16 |
| GET | `/metas` | Lista jefes activos y su vida | Sí | HU-18 |
| PUT | `/misiones/{id}` | Incluye `metaId` para vincularla | Sí | HU-17 |
| GET | `/metas/historial` | Jefes ya derrotados | Sí | HU-19 |

### EP-06 · Insignias

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| GET | `/insignias` | Catálogo completo de insignias | Sí | HU-20 |
| GET | `/personajes/me/insignias` | Insignias obtenidas y bloqueadas | Sí | HU-21 |

### EP-07 · Google Calendar

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| GET | `/google/conectar` | Inicia el flujo OAuth2 | Sí | HU-22 |
| DELETE | `/google/desconectar` | Revoca el acceso a Calendar | Sí | HU-23 |

> La sincronización de HU-24 ocurre automáticamente dentro de los endpoints de `/misiones`, no
> es un endpoint aparte.

### EP-08 · Administración

| Método | Endpoint | Descripción | Auth | HU |
|---|---|---|---|---|
| GET | `/admin/usuarios` | Lista todos los usuarios | Sí (ADMIN) | HU-25 |
| GET | `/admin/estadisticas` | Totales de uso de la plataforma | Sí (ADMIN) | HU-25 |
| PUT | `/admin/clases` `/admin/niveles` `/admin/insignias` | Administra el catálogo base | Sí (ADMIN) | HU-21 |

**Total: 18 rutas.**
