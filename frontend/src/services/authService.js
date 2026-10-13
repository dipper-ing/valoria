const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080'

async function request(path, body) {
  let response

  try {
    response = await fetch(`${API_URL}${path}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    })
  } catch {
    throw new Error('No se pudo conectar con el servidor. Verifica que el backend esté ejecutándose.')
  }

  const data = await response.json().catch(() => ({}))

  if (!response.ok) {
    const error = new Error(data.mensaje ?? 'Ocurrió un error inesperado.')
    error.fieldErrors = data.errores ?? {}
    throw error
  }

  return data
}

export function register(credentials) {
  return request('/auth/registro', credentials)
}

export function login(credentials) {
  return request('/auth/login', credentials)
}

export function getAuthHeaders() {
  const token = localStorage.getItem('valoria_token')
  return token ? { Authorization: `Bearer ${token}` } : {}
}
