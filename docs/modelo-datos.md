# VALORIA — Modelo de datos (ERD)

> Fuentes originales: [`VALORIA_modelo_ER.drawio`](VALORIA_modelo_ER.drawio) y
> [`valoria_erd_actualizado.html`](valoria_erd_actualizado.html). Este archivo es una
> transcripción en Mermaid para lectura rápida — el drawio es la fuente editable.

```mermaid
erDiagram
    USUARIO ||--|| PERSONAJE : "1"
    PERSONAJE }o--|| CLASE_PERSONAJE : "N:1"
    PERSONAJE ||--o{ MISION : "1"
    PERSONAJE ||--o{ META : "1"
    MISION }o--o{ META : "MISION_META"
    PERSONAJE }o--o{ INSIGNIA : "PERSONAJE_INSIGNIA"

    CLASE_PERSONAJE {
        bigint id PK
        varchar nombre
        varchar descripcion
    }

    NIVEL {
        bigint id PK
        int nivel UK
        varchar nombre_rango
        int xp_requerido
    }

    USUARIO {
        bigint id PK
        varchar correo UK
        varchar contrasena_hash
        varchar proveedor_auth
        varchar google_refresh_token
        varchar rol
    }

    PERSONAJE {
        bigint id PK
        bigint usuario_id FK
        bigint clase_personaje_id FK
        varchar nombre
        int nivel
        int xp_actual
        int oro
        int racha_general_dias
        int pociones_disponibles
        date ultima_actividad_fecha
    }

    MISION {
        bigint id PK
        bigint personaje_id FK
        varchar nombre
        varchar frecuencia
        varchar estado
        int racha_dias
        date ultima_completada_fecha
        int xp_recompensa
        int oro_recompensa
        varchar google_event_id
    }

    META {
        bigint id PK
        bigint personaje_id FK
        varchar nombre
        text descripcion
        int vida_total
        int vida_actual
        varchar estado
    }

    MISION_META {
        bigint mision_id FK
        bigint meta_id FK
        int dano_por_completar
    }

    INSIGNIA {
        bigint id PK
        varchar nombre
        varchar condicion
    }

    PERSONAJE_INSIGNIA {
        bigint personaje_id FK
        bigint insignia_id FK
        timestamp fecha_obtencion
    }
```

## Puntos clave del diseño

- `USUARIO` 1:1 `PERSONAJE` — un personaje por usuario.
- `CLASE_PERSONAJE` y `NIVEL` son **catálogos propios desde el inicio** (decisión tomada), no
  texto libre — el admin los gestiona sin tocar código vía `/admin/clases` y `/admin/niveles`.
  `PERSONAJE.clase_personaje_id` es FK a `CLASE_PERSONAJE`. La tabla `NIVEL` guarda
  `nombre_rango` y `xp_requerido` por nivel, aunque el cálculo de XP se hace por fórmula (ver
  regla en `requisitos.md`) — la tabla sirve para cachear el valor y para nombres de rango
  personalizados.
- `PERSONAJE` 1:N `MISION` y 1:N `META`.
- `MISION` N:N `META` vía tabla intermedia `MISION_META` (campo `dano_por_completar`, con valor
  fijo según la frecuencia de la misión — ver regla de negocio en `requisitos.md`).
- `PERSONAJE` N:N `INSIGNIA` vía tabla intermedia `PERSONAJE_INSIGNIA`.
- `MISION.google_event_id` — agregado al modelo para guardar el ID del evento de Calendar
  vinculado (permite editarlo/eliminarlo desde la app).
- `PERSONAJE.pociones_disponibles` — agregado al modelo para el sistema de poción (HU-15); se
  incrementa cada 7 días de `racha_general_dias`.
- `PERSONAJE.racha_general_dias` es la racha "oficial" mostrada en la hoja de personaje;
  `MISION.racha_dias` es la racha individual por misión (para HU-13/HU-14 a nivel de cada
  hábito). Ambas coexisten con propósitos distintos.
- Buenas prácticas pendientes de aplicar en el script SQL: `estado`/`frecuencia` como ENUM o
  CHECK, columnas `created_at`/`updated_at` en todas las tablas, restricción de unicidad en
  `MISION_META` (mision_id+meta_id) y `PERSONAJE_INSIGNIA` (personaje_id+insignia_id).
