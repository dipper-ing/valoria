# VALORIA Frontend

Cliente React + Vite para el flujo de autenticación de VALORIA.

## Ejecutar en local

Desde la carpeta `frontend`:

```powershell
npm install
Copy-Item .env.example .env
npm run dev
```

La aplicación queda disponible en `http://localhost:5173`. El backend debe estar
ejecutándose en `http://localhost:8080`.

`VITE_API_URL` permite cambiar la URL del backend sin modificar el código.

## Flujo implementado

- Registro mediante `POST /auth/registro`.
- Login mediante `POST /auth/login`.
- Mensajes de validación por campo usando `errores`.
- Persistencia del JWT en `localStorage` como `valoria_token`.
- Usuario autenticado guardado como `valoria_user`.
- `getAuthHeaders()` listo para enviar `Authorization: Bearer <token>` en rutas protegidas.
