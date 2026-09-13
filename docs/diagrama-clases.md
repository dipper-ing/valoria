# VALORIA — Diagrama de clases / paquetes del backend

> Fuente original: [`valoria_diagrama_clases_completo.html`](valoria_diagrama_clases_completo.html).
> Transcripción textual para referencia rápida.

## Flujo por capas (Controller → Service → Repository → Entity)

```
AuthController          → AuthService          → JwtService
  +login()                 +autenticar()          +generarToken()
  +registro()               +registrar()          +validarToken()
  +loginGoogle()          → UsuarioRepository «interface»
                              +findByCorreo()
                            → Usuario
                              -Long id, -String correo, -String rol

PersonajeController      → PersonajeService
  +obtenerMiPersonaje()     +crearPersonaje()
  +actualizarPersonaje()    +sumarXp()
                            +usarPocion()
                          → PersonajeRepository «interface»
                              +findByUsuarioId()
                            → Personaje
                              -Long id, -String nombre, -Integer nivel, -Integer xpActual
                          → ClasePersonajeRepository «interface»
                              +findAll()
                          → NivelRepository «interface»
                              +findByNivel()

MisionController          → MisionService
  +crearMision()             +completar()
  +completarMision()         +calcularRecompensa()
                            → MisionRepository «interface»
                                +findByPersonajeId()
                              → Mision
                                -Long id, -String nombre, -String frecuencia, -Integer rachaDias

MetaController            → MetaService
  +crearMeta()               +aplicarDano()
                             +verificarDerrota()
                            → MetaRepository «interface»
                                +findByPersonajeId()
                              → Meta
                                -Long id, -Integer vidaTotal, -Integer vidaActual

InsigniaController        → InsigniaService          → InsigniaRepository «interface»
  +listarCatalogo()          +evaluarLogros()             +findAll()
  +listarDePersonaje()       +listarCatalogo()
                             +listarDePersonaje()       → Insignia
                                                            -Long id, -String nombre
```

## Notas de diseño

- El `Controller` nunca toca el `Repository` directamente — siempre pasa por el `Service`
  (regla explícita en `CLAUDE.md`, respetada en todo el diagrama).
- `PersonajeService` es el punto de contacto entre varias operaciones (crear personaje, sumar
  XP, usar poción) — concentra bastante lógica; vigilar que no crezca en un "god service" al
  implementar (podría separarse `PocionService` si crece mucho).
- `MisionService.calcularRecompensa()` y `MetaService.aplicarDano()`/`verificarDerrota()` son
  donde vive la regla de negocio de RF05/RF07 — el backend calcula, nunca confía en el frontend
  (RNF de seguridad).
- `InsigniaController` se agregó para cubrir `GET /insignias` y
  `GET /personajes/me/insignias` del contrato de API. `evaluarLogros()` se dispara internamente
  (llamado desde `MisionService` o `MetaService` tras completar una misión o derrotar un jefe),
  consistente con HU-20 ("el sistema evalúa las condiciones automáticamente").
- Pendiente en el diagrama (a completar cuando se implemente): `GoogleCalendarService` (EP-07)
  y los componentes de administración (EP-08), mencionados en `CLAUDE.md` pero no dibujados
  todavía en este diagrama de clases.
