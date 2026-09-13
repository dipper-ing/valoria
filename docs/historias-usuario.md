# VALORIA — Historias de Usuario (HU-01 a HU-26)

> MVP priorizado (ver `CLAUDE.md`): HU-01, 02, 05, 07, 08, 09, 11, 12, 13, 14.

## EP-01 — Cuenta y autenticación

### FE-01: Registro y login propio (correo/contraseña + JWT)

**HU-01 — Registro con correo y contraseña**
Como visitante, quiero crear una cuenta con mi correo y una contraseña, para empezar a usar
VALORIA con mi propio progreso guardado.
- El sistema valida que el correo no esté ya registrado
- La contraseña se guarda cifrada (nunca en texto plano)
- Si el registro es exitoso, el usuario queda logueado automáticamente con un token JWT
- Se muestran mensajes de error claros si el correo es inválido o la contraseña es muy corta
- Prioridad: Must have | Tamaño: M

**HU-02 — Inicio de sesión con correo y contraseña**
Como usuario registrado, quiero iniciar sesión con mi correo y contraseña, para acceder a mi
progreso guardado.
- Si las credenciales son correctas, se genera un token JWT válido con expiración definida
- Si son incorrectas, se muestra un error genérico (sin indicar si falló el correo o la
  contraseña)
- Prioridad: Must have | Tamaño: M

**HU-03 — Cerrar sesión**
Como usuario logueado, quiero cerrar sesión, para proteger mi cuenta si uso un dispositivo
compartido.
- Al cerrar sesión se elimina el token del cliente
- El usuario es redirigido a la pantalla de login
- Prioridad: Must have | Tamaño: S

### FE-02: Login con Google (OAuth2)

**HU-04 — Inicio de sesión con Google**
Como usuario, quiero iniciar sesión con mi cuenta de Google, para no tener que crear ni
recordar otra contraseña.
- Si es la primera vez que entra con Google, se crea la cuenta automáticamente con rol Jugador
- Si ya existe una cuenta con ese correo, se vincula en vez de duplicarla
- El usuario queda autenticado con el mismo sistema de sesión (JWT) que el login propio
- Prioridad: Should have | Tamaño: L

## EP-02 — Personaje

### FE-03: Creación y edición de personaje (nombre, clase)

**HU-05 — Creación de personaje al registrarse**
Como usuario nuevo, quiero crear mi personaje eligiendo un nombre y una clase, para que
represente mi progreso dentro de VALORIA.
- Debe elegir entre las clases disponibles (Guerrero, Mago, Clérigo, Pícaro)
- El personaje inicia en nivel 1 con 0 XP
- No se puede continuar en la app sin completar este paso
- Prioridad: Must have | Tamaño: M

**HU-06 — Editar perfil de personaje**
Como usuario, quiero editar el nombre y avatar de mi personaje, para personalizar mi
experiencia.
- Puede cambiar nombre visible y avatar entre opciones predefinidas
- Puede cambiar la clase del personaje
- Prioridad: Could have | Tamaño: S

### FE-04: Hoja de personaje (visualización)

**HU-07 — Ver hoja de personaje**
Como usuario, quiero ver mi hoja de personaje, para conocer mi nivel, XP actual, racha y clase
de un vistazo.
- Muestra nivel, barra de XP hacia el siguiente nivel, racha activa y clase
- Se actualiza en tiempo real al completar una misión
- Prioridad: Must have | Tamaño: M

## EP-03 — Misiones y progreso

### FE-05: Gestión de misiones (crear, editar, frecuencia)

**HU-08 — Crear misión**
Como jugador, quiero crear una misión a partir de un hábito real, para empezar a ganar
recompensas por cumplirlo.
- Debe indicar nombre, frecuencia (diaria/semanal) y categoría (ligada a una clase de personaje)
- La misión aparece inmediatamente en la lista de misiones activas
- Prioridad: Must have | Tamaño: M

**HU-09 — Ver lista de misiones activas**
Como jugador, quiero ver todas mis misiones del día, para saber qué me falta por completar.
- Se muestran separadas las completadas de las pendientes
- Cada misión indica su recompensa de XP/oro
- Prioridad: Must have | Tamaño: S

**HU-10 — Editar o eliminar misión**
Como jugador, quiero editar o eliminar una misión, para ajustarla si cambian mis metas.
- Se puede editar nombre, frecuencia y categoría
- Al eliminar, se pide confirmación y no se pierde el historial ya generado
- Prioridad: Should have | Tamaño: S

### FE-06: Completar misión y cálculo de XP/oro

**HU-11 — Completar misión**
Como jugador, quiero marcar una misión como completada, para recibir mi recompensa de XP y oro.
- Al completarla, se suma el XP/oro correspondiente al personaje
- No se puede completar dos veces la misma misión el mismo día
- El cálculo de recompensa se hace en el backend, nunca confiando en el valor enviado por el
  frontend
- Prioridad: Must have | Tamaño: M

### FE-07: Subida de nivel

**HU-12 — Subir de nivel automáticamente**
Como jugador, quiero subir de nivel cuando acumulo suficiente XP, para sentir que mi esfuerzo
tiene una recompensa visible.
- Existe una tabla de XP requerido por nivel
- Al subir de nivel se dispara una notificación/animación de "level up"
- El nivel queda reflejado inmediatamente en la hoja de personaje
- Prioridad: Must have | Tamaño: M

## EP-04 — Racha

### FE-08: Registro y ruptura de racha

**HU-13 — Mantener racha al completar a tiempo**
Como jugador, quiero que mi racha aumente cada vez que cumplo una misión dentro de su
frecuencia, para tener un indicador visual de mi constancia.
- La racha sube un punto por cada ciclo cumplido (día o semana según frecuencia)
- Prioridad: Must have | Tamaño: M

**HU-14 — Romper racha por inactividad**
Como jugador, quiero que mi racha se reinicie si dejo pasar el plazo sin completar la misión,
para que el sistema refleje mi constancia real.
- Si pasa el plazo sin completarse, la racha vuelve a cero
- El sistema notifica visualmente cuando una racha está "en riesgo" (ej. faltan pocas horas)
- Prioridad: Must have | Tamaño: M

### FE-09: Sistema de "poción" (perdón de falla)

**HU-15 — Usar poción para proteger la racha**
Como jugador, quiero usar un ítem especial para perdonar una falla, para no perder toda mi
racha por un mal día.
- El jugador recibe pociones limitadas (ej. una por semana)
- Al usarla, la racha no se rompe aunque no se complete la misión ese día
- Se descuenta del inventario de pociones disponibles
- Prioridad: Could have | Tamaño: M

## EP-05 — Jefes (metas grandes)

### FE-10: Gestión de metas/jefes

**HU-16 — Crear un jefe (meta a largo plazo)**
Como jugador, quiero crear una meta grande con "puntos de vida", para trabajar hacia un
objetivo más ambicioso que un hábito diario.
- Se define nombre, puntos de vida totales y descripción de la meta
- El jefe aparece en una sección de "batallas activas"
- Prioridad: Should have | Tamaño: L

### FE-11: Vinculación misión↔meta y barra de vida

**HU-17 — Vincular misiones a un jefe**
Como jugador, quiero asociar una o varias misiones a un jefe, para que completarlas le reste
vida.
- Al crear o editar una misión, puedo elegir a qué jefe (opcional) está vinculada
- Un jefe puede tener varias misiones vinculadas
- Prioridad: Should have | Tamaño: M

**HU-18 — Ver barra de vida del jefe**
Como jugador, quiero ver cómo baja la vida del jefe cuando completo misiones vinculadas, para
visualizar mi progreso hacia la meta.
- Cada misión completada vinculada resta el valor de daño correspondiente
- La barra de vida se actualiza en tiempo real en la pantalla del jefe
- Prioridad: Should have | Tamaño: M

### FE-12: Derrota del jefe (cierre de meta)

**HU-19 — Derrotar al jefe**
Como jugador, quiero que se marque el jefe como derrotado al llegar a 0 de vida, para cerrar
formalmente esa meta grande.
- Al llegar a 0, el jefe pasa a estado "derrotado" y no puede recibir más daño
- Se otorga una recompensa especial (XP extra, insignia)
- El jefe derrotado queda visible en un historial de "metas cumplidas"
- Prioridad: Should have | Tamaño: M

## EP-06 — Logros e insignias

### FE-13: Definición y otorgamiento de insignias

**HU-20 — Desbloquear insignias automáticamente**
Como jugador, quiero desbloquear insignias al alcanzar hitos (ej. racha de 7 días, primer jefe
derrotado), para sentir reconocimiento por mis avances.
- Existe un catálogo de logros con su condición de desbloqueo
- El sistema evalúa las condiciones automáticamente al completar acciones relevantes
- Al cumplirse, se notifica al usuario y se guarda con fecha de obtención
- Prioridad: Should have | Tamaño: M

### FE-14: Visualización de insignias en el perfil

**HU-21 — Ver galería de insignias**
Como jugador, quiero ver todas mis insignias obtenidas y las que me faltan, para saber qué
retos me quedan.
- Se listan insignias obtenidas (con fecha) e insignias bloqueadas
- Prioridad: Could have | Tamaño: S

## EP-07 — Integración con Google Calendar

### FE-15: Conectar/desconectar cuenta de Google

**HU-22 — Conectar cuenta de Google Calendar**
Como jugador, quiero autorizar el acceso a mi Google Calendar, para poder sincronizar mis
misiones más adelante.
- Se solicita permiso vía OAuth2 con los scopes mínimos necesarios de Calendar
- El estado de conexión queda visible en el perfil (conectado/no conectado)
- Prioridad: Should have | Tamaño: L

**HU-23 — Desconectar cuenta de Google Calendar**
Como jugador, quiero poder revocar el acceso a mi Google Calendar, para dejar de compartir esa
información si ya no lo necesito.
- Al desconectar, se revoca el token de acceso guardado
- Las misiones dejan de sincronizarse hasta que se vuelva a conectar
- Prioridad: Should have | Tamaño: S

### FE-16: Sincronizar misión → evento de calendario

**HU-24 — Sincronizar misión con Google Calendar**
Como jugador con cuenta de Google conectada, quiero que mis misiones se reflejen como eventos
en mi calendario, para no perderlas de vista fuera de la app.
- Cada misión con frecuencia definida crea un evento correspondiente en Google Calendar
- Si se edita o elimina la misión en VALORIA, el evento se actualiza o elimina en Calendar
- Prioridad: Should have | Tamaño: L

## EP-08 — Administración

### FE-17: Panel de estadísticas de uso

**HU-25 — Ver estadísticas generales de uso**
Como administrador, quiero ver un panel con estadísticas básicas de la plataforma, para
entender cómo se está usando VALORIA.
- Muestra total de usuarios registrados, misiones completadas y jefes derrotados
- Solo accesible con rol ADMIN
- Prioridad: Could have | Tamaño: M

### FE-18: Rol y permisos de administrador

**HU-26 — Restringir funciones administrativas por rol**
Como administrador, quiero que solo las cuentas con rol ADMIN puedan acceder a funciones
administrativas, para proteger la información de los usuarios.
- Los endpoints administrativos verifican el rol antes de ejecutar cualquier acción
  (`@PreAuthorize`)
- Un usuario con rol Jugador recibe error 403 si intenta acceder
- El primer usuario ADMIN se define manualmente en base de datos
- Prioridad: Must have | Tamaño: M
