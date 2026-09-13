# VALORIA — Requisitos

## Requisitos Funcionales (RF)

- **RF01 — Registro propio**: el usuario puede registrarse con correo y contraseña (correo
  único, contraseña mínimo 8 caracteres). Al loguear, recibe un token JWT.
- **RF02 — Login con Google**: el usuario puede iniciar sesión con su cuenta de Google. Si es
  su primer login así, se le crea personaje automáticamente vinculado a ese correo.
- **RF03 — Crear personaje**: al registrarse, el usuario define nombre y clase
  (Guerrero/Mago/Clérigo/Pícaro). La clase es obligatoria y se puede cambiar después desde el
  perfil.
- **RF04 — Crear misión**: el usuario puede crear una misión con nombre, clase asociada y
  frecuencia (única/diaria/semanal). La frecuencia determina cuándo se puede volver a completar.
- **RF05 — Completar misión → XP**: al marcar una misión como completada, el usuario gana XP y
  oro de inmediato. Si cruza el umbral de nivel, sube de nivel (con animación de "level up").
- **RF06 — Racha**: la racha se mantiene si se completa al menos una misión al día, y se rompe
  si pasan 24h sin completar ninguna. Existe una "poción" que perdona una falla (regla exacta a
  definir).
- **RF07 — Jefes (metas grandes)**: una meta grande se vincula a varias misiones; el jefe tiene
  una barra de vida que baja con cada misión relacionada completada, y "cae" cuando se completan
  todas.
- **RF08 — Insignias/logros**: se desbloquean insignias al cumplir hitos (ej. racha de 7 días).
- **RF09 — Sincronización con Google Calendar (opcional)**: el usuario puede conectar su cuenta
  de Google desde ajustes para ver sus misiones reflejadas en su calendario real. Es un paso
  aparte y voluntario (botón "Conectar Google Calendar"), independiente de cómo inició sesión;
  si no lo conecta, el resto de la app funciona con normalidad.
- **RF10 — Rol administrador**: el administrador puede ver estadísticas generales de uso.

## Requisitos No Funcionales (RNF)

- **Seguridad**: contraseñas hasheadas (BCrypt), JWT con expiración de 24h.
- **Rendimiento**: endpoints comunes responden en <500ms bajo uso normal.
- **Usabilidad**: estética pixel-RPG consistente en todas las pantallas (paleta y tipografía
  definidas en `CLAUDE.md`, sección "Dirección de arte").
- **Disponibilidad**: no crítica 24/7 (proyecto académico), se acepta downtime en despliegues.
- **Compatibilidad**: Chrome/Firefox actualizados, responsive básico para laptop.

## Reglas de negocio definidas

- **Poción (RF06 / HU-15)**: se otorga 1 poción cada vez que `racha_general_dias` del
  personaje llega a un múltiplo de 7 (día 7, 14, 21…). No es periódica por calendario, sino por
  constancia. A definir en implementación: tope máximo de pociones acumulables en inventario
  (por ahora, sin tope explícito — revisar si hace falta uno para evitar acumulación infinita
  en rachas muy largas).
- **Daño al jefe (RF07)**: cada misión tiene un daño **fijo según su frecuencia**, independiente
  del jefe al que esté vinculada (ej. diaria = 5, semanal = 15 — valores exactos a afinar en
  balanceo). El campo `MISION_META.dano_por_completar` se llena con ese valor fijo al vincular
  la misión. Consecuencia aceptada: el jefe puede tardar un número variable de misiones en caer
  (no está garantizado que llegue exacto a 0 con la última misión completada) — el
  `MetaService.verificarDerrota()` debe usar `vida_actual <= 0`, no `== 0`.
- **Curva de XP por nivel (HU-12)**: fórmula exponencial suave `xp_requerido(nivel) =
  round(100 * nivel^1.5)`. Ejemplos: nivel 2 ≈ 283, nivel 5 ≈ 1118, nivel 10 ≈ 3162. Se calcula
  en código (no en tabla `NIVEL` — ver sección de catálogos en `modelo-datos.md`), pero la tabla
  `NIVEL` igual existe como catálogo para otros atributos por nivel (ej. nombre de rango,
  recompensas especiales) si se quieren agregar más adelante.
