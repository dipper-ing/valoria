# VALORIA — Contexto del proyecto para Claude Code

> Este archivo se lee automáticamente al iniciar una sesión de Claude Code en este repo.
> Mantenlo actualizado a medida que avanza el proyecto — es la fuente de verdad del contexto.
>
> Documentos fuente completos en `docs/`: `requisitos.md` (RF/RNF + reglas de negocio),
> `historias-usuario.md` (26 HUs), `api-contrato.md` (18 endpoints), `modelo-datos.md` (ERD),
> `diagrama-clases.md`, `arquitectura.md`.

## 1. Qué es VALORIA

VALORIA es una aplicación web de hábitos y metas personales con estética de videojuego RPG
pixel-art. Es un proyecto académico de dos estudiantes de Ingeniería en Sistemas (8vo semestre)
desarrollado durante vacaciones como reto de aprendizaje full stack y pieza de portafolio.

Cada hábito real del usuario se convierte en una **misión**. La constancia se representa como
una **racha** (llama que crece o se apaga). Las metas grandes a largo plazo son **jefes** que
solo se derrotan completando suficientes misiones vinculadas. El usuario sube de **nivel**,
gana **XP y oro**, y desbloquea **insignias**.

**Objetivo del reto**: vivir un ciclo completo de desarrollo de software como en la industria
(descubrimiento → diseño → construcción → pruebas → despliegue), con Scrum real y roles rotativos.

## 2. Equipo y roles

Dos desarrolladores rotando cada sprint (1 semana) por los seis roles: Scrum Master, Product
Owner, Frontend, Backend, Base de Datos, DevOps/Azure. Ningún rol es fijo de una persona.

## 3. Stack técnico

| Capa | Tecnología |
|---|---|
| Backend | Java 17 + Spring Boot (Web, JPA, Security, Validation) |
| Base de datos | PostgreSQL |
| Seguridad | Spring Security + JWT + OAuth2 con Google |
| Frontend | React + Vite |
| Integración externa | Google Calendar API |
| Nube (backend + DB) | Microsoft Azure (App Service, Azure Database for PostgreSQL) |
| Nube (frontend) | Vercel |
| CI/CD | GitHub Actions |
| Documentación de API | OpenAPI/Swagger (`springdoc-openapi`) |

## 4. Estado actual del checklist (por fases)

- [x] **Fase 0 — Preparación del equipo**: roles definidos, tablero Scrum, Definition of Ready
      y Definition of Done acordados (ver sección 5), duración de sprint: 1 semana.
- [x] **Fase 1 — Descubrimiento y requisitos**: visión de producto redactada, roles de usuario
      dentro de la app definidos (Jugador/Administrador, ver sección 8), 26 Historias de Usuario
      redactadas y priorizadas en 8 épicas (ver sección 6).
- [x] **Fase 2 — Diseño**: arquitectura general definida, modelo entidad-relación completo,
      diagrama de clases/paquetes del backend, contrato de API con 18 endpoints documentado
      en OpenAPI (`openapi.yaml` en la raíz del repo). Pendiente dentro de esta fase: wireframes
      visuales en Figma (el sistema de diseño conceptual ya está definido, ver sección 9).
- [ ] **Fase 3 — Configuración técnica**: *siguiente paso*. Crear proyecto Spring Boot, proyecto
      frontend React+Vite, PostgreSQL local, recursos en Azure, pipeline CI/CD, credenciales
      OAuth2 de Google.
- [ ] **Fase 4 — Backend**: entidades JPA, repositorios, servicios, controllers, JWT, login con
      Google, documentación Swagger.
- [ ] **Fase 5 — Frontend**: pantallas (login, hoja de personaje, misiones, mapa, perfil),
      conexión con la API, estética pixel-RPG.
- [ ] **Fase 6 — Integraciones externas**: sincronización de misiones con Google Calendar.
- [ ] **Fase 7 — Pruebas**: Postman, JUnit + Mockito, pruebas de integración, QA manual.
- [ ] **Fase 8 — Despliegue**: Azure App Service, Azure Database for PostgreSQL, Azure Static
      Web Apps, dominio/HTTPS, monitoreo.
- [ ] **Fase 9 — Cierre y portafolio**: README, demo en video, retrospectiva, publicación.

## 5. Definition of Ready / Definition of Done

**Definition of Ready** (antes de meter una historia al sprint):
- Formato "Como/Quiero/Para" + criterios de aceptación definidos
- PO y devs entienden la historia igual
- Endpoints/pantallas/tablas involucradas identificadas
- Sin dependencias bloqueantes
- Estimación asignada (S/M/L)

**Definition of Done** (para cerrar una historia):
- Cumple los criterios de aceptación
- Corre sin errores en local
- Endpoint probado en Postman / frontend probado con datos reales
- Pull Request revisado por el compañero
- Sin credenciales ni llaves hardcodeadas
- No rompe funcionalidad existente
- README actualizado si aplica

## 6. Épicas y features

| Épica | Features |
|---|---|
| EP-01 Cuenta y autenticación | FE-01 Registro/login propio + JWT · FE-02 Login con Google |
| EP-02 Personaje | FE-03 Creación/edición de personaje · FE-04 Hoja de personaje |
| EP-03 Misiones y progreso | FE-05 Gestión de misiones · FE-06 Completar misión y XP/oro · FE-07 Subida de nivel |
| EP-04 Racha | FE-08 Registro/ruptura de racha · FE-09 Sistema de poción |
| EP-05 Jefes (metas) | FE-10 Gestión de metas · FE-11 Vinculación misión↔meta · FE-12 Derrota del jefe |
| EP-06 Logros e insignias | FE-13 Otorgamiento de insignias · FE-14 Visualización en perfil |
| EP-07 Google Calendar | FE-15 Conectar/desconectar cuenta · FE-16 Sincronizar misión→evento |
| EP-08 Administración | FE-17 Panel de estadísticas · FE-18 Rol y permisos de administrador |

26 Historias de Usuario en total (HU-01 a HU-26), todas con criterios de aceptación definidos.
MVP priorizado para el alcance de vacaciones: HU-01, 02, 05, 07, 08, 09, 11, 12, 13, 14 (auth
propio + personaje + misiones + XP/nivel + racha básica). El resto (Google login, jefes,
insignias, Calendar, admin) es extensión sobre ese MVP.

## 7. Modelo de datos (resumen — ver ERD completo en `docs/modelo-datos.md`)

Entidades: `USUARIO`, `PERSONAJE`, `CLASE_PERSONAJE`, `NIVEL`, `MISION`, `META`, `MISION_META`,
`INSIGNIA`, `PERSONAJE_INSIGNIA`.

Puntos clave del diseño:
- `USUARIO` 1:1 `PERSONAJE` — un personaje por usuario.
- `CLASE_PERSONAJE` y `NIVEL` son catálogos propios (no texto libre) para que el admin los
  gestione sin tocar código.
- `PERSONAJE` 1:N `MISION` y 1:N `META`.
- `MISION` N:N `META` vía tabla intermedia `MISION_META` (campo `dano_por_completar`).
- `PERSONAJE` N:N `INSIGNIA` vía tabla intermedia `PERSONAJE_INSIGNIA`.
- `MISION.google_event_id` guarda el ID del evento de Calendar vinculado (para poder
  editarlo/eliminarlo desde la app).
- `PERSONAJE.pociones_disponibles` soporta el sistema de "perdón de racha" (HU-15).
- Buenas prácticas pendientes de aplicar en el script SQL: `estado`/`frecuencia` como ENUM o
  CHECK, columnas `created_at`/`updated_at` en todas las tablas, restricción de unicidad en
  `MISION_META` (mision_id+meta_id) y `PERSONAJE_INSIGNIA` (personaje_id+insignia_id).

### Reglas de negocio ya definidas

- **Poción (HU-15)**: se otorga 1 poción cada vez que `racha_general_dias` llega a un múltiplo
  de 7. Al usarla, no se rompe la racha aunque no se complete la misión ese día.
- **Daño al jefe (RF07)**: fijo según la frecuencia de la misión (ej. diaria = 5, semanal = 15;
  valores exactos a afinar en balanceo), guardado en `MISION_META.dano_por_completar`. La
  derrota se evalúa con `vida_actual <= 0`, no `== 0` (el jefe puede caer con daño de sobra).
- **Curva de XP por nivel (HU-12)**: `xp_requerido(nivel) = round(100 * nivel^1.5)`.

## 8. Roles dentro de la aplicación (no confundir con roles del equipo)

| Acción | Jugador | Administrador |
|---|---|---|
| Crear/editar sus misiones | Sí | Sí |
| Ver hoja de personaje propia | Sí | Sí |
| Ver datos de otros usuarios | No | Sí |
| Administrar catálogo (clases, niveles, logros) | No | Sí |
| Sincronizar con Google Calendar | Sí | Sí |
| Desactivar cuentas | No | Sí |

Implementación: enum `Role { PLAYER, ADMIN }` en `Usuario`. Registro normal siempre asigna
`PLAYER`. El primer `ADMIN` se crea manualmente en base de datos. Endpoints administrativos
protegidos con `@PreAuthorize("hasRole('ADMIN')")`.

## 9. Arquitectura

Capas del backend, en orden de flujo de una petición autenticada:

```
Cliente (React) → Security (JWT filter) → Controller → Service → Repository → PostgreSQL
```

- El Controller nunca toca el Repository directamente — siempre pasa por el Service.
- `GoogleCalendarService` es el único componente que además de hablar con la base de datos,
  habla con una API externa (Google Calendar).
- Todo el backend corre en Azure App Service; la base de datos en Azure Database for
  PostgreSQL; el frontend se despliega en **Vercel** (fuera de Azure — requiere configurar
  CORS en el backend para el dominio de Vercel).

Mapa de componentes por épica (Controller → Service → Repository), ya definido para EP-01,
EP-02, EP-03, EP-05 y EP-06. EP-07 (Calendar) y EP-08 (Admin) reutilizan repositorios
existentes en vez de tener los suyos propios.

## 10. Contrato de API

18 endpoints documentados — ver `docs/api-contrato.md` (resumen navegable) y
`docs/VALORIA_contrato_API.pdf` (documento original). El `openapi.yaml` formal en OpenAPI 3.0
**aún no existe**: se generará con `springdoc-openapi` a partir de las anotaciones del código
durante la Fase 4, en vez de escribirse a mano por adelantado.

Convenciones:
- Recursos en **plural** (`/misiones`, no `/mision`).
- El verbo de la acción va en el **método HTTP**, nunca en la URL.
- Acciones que no son CRUD puro se modelan como sub-recurso: `POST /misiones/{id}/completar`.
- Todos los endpoints requieren JWT (`Authorization: Bearer <token>`) excepto
  `/auth/registro`, `/auth/login` y `/auth/google`.

## 11. Traducción de mecánicas (gamificación) — para nombrar clases/variables con sentido

| Concepto real | En el código / dominio |
|---|---|
| Hábito o tarea | Misión (`Mision`) |
| Constancia diaria | Racha (`rachaDias`) |
| Categoría del hábito | Clase de personaje (Guerrero=físico, Mago=estudio, Clérigo=bienestar, Pícaro=hábitos rápidos) |
| Completar tarea | XP y oro (`xpRecompensa`, `oroRecompensa`) |
| Progreso acumulado | Nivel (`nivel`, tabla `NIVEL` con `xp_requerido`) |
| Meta grande a largo plazo | Jefe / Meta (`Meta`, con `vidaTotal`/`vidaActual`) |
| Logro especial | Insignia (`Insignia`) |
| Perdonar una falla | Poción (`pocionesDisponibles`) |

## 12. Dirección de arte (para el frontend)

Estética RPG pixel-art de 16-bits, no minimalismo. Paleta base:

| Uso | Color |
|---|---|
| Fondo | `#14131F` |
| Panel | `#2C2412` |
| Oro / XP | `#E8B23A` |
| Vida / daño | `#7B2D3D` |
| Éxito | `#4CAF7D` |
| Maná / info | `#3A6EA5` |

Tipografía pixel (ej. Press Start 2P) solo para títulos cortos; tipografía legible para
párrafos. Marcos tipo "ventana de diálogo" de RPG: bordes gruesos, esquinas cuadradas, sombras
duras sin blur. Barra de vida/XP segmentada, no lisa.

## 13. Convenciones de código a seguir

- snake_case en la base de datos, camelCase en Java/JS, PascalCase en nombres de clase.
- Nombres de variables y entidades en español, alineados con el dominio (`Mision`, `Personaje`,
  `Meta`), no traducidos a inglés a medias.
- Sin credenciales, contraseñas ni llaves hardcodeadas — todo por variables de entorno.
- Cálculos de recompensa (XP, oro, daño a jefes) siempre en el backend, nunca confiando en
  valores enviados desde el frontend.

## 14. Siguiente paso inmediato

Arrancar la **Fase 3**: crear el proyecto Spring Boot, el proyecto frontend, configurar
PostgreSQL local, y las cuentas de Azure y Google Cloud Console.
