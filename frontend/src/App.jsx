import { useState } from 'react'
import './App.css'
import { login, register } from './services/authService'
import valoriaTown from './assets/valoria-town.png'

const emptyForm = { correo: '', contrasena: '' }

function App() {
  const [mode, setMode] = useState('login')
  const [form, setForm] = useState(emptyForm)
  const [errors, setErrors] = useState({})
  const [message, setMessage] = useState('')
  const [user, setUser] = useState(() => {
    const savedUser = localStorage.getItem('valoria_user')
    return savedUser ? JSON.parse(savedUser) : null
  })

  const isLogin = mode === 'login'

  function changeMode(nextMode) {
    setMode(nextMode)
    setForm(emptyForm)
    setErrors({})
    setMessage('')
  }

  function handleChange(event) {
    setForm({ ...form, [event.target.name]: event.target.value })
    setErrors({ ...errors, [event.target.name]: '' })
    setMessage('')
  }

  async function handleSubmit(event) {
    event.preventDefault()
    setErrors({})
    setMessage('')

    try {
      const response = isLogin ? await login(form) : await register(form)
      localStorage.setItem('valoria_token', response.token)
      localStorage.setItem('valoria_user', JSON.stringify(response.usuario))
      setUser(response.usuario)
      setForm(emptyForm)
      setMessage(isLogin ? 'Has iniciado sesión correctamente.' : 'Cuenta creada correctamente.')
    } catch (error) {
      setMessage(error.message)
      setErrors(error.fieldErrors ?? {})
    }
  }

  function logout() {
    localStorage.removeItem('valoria_token')
    localStorage.removeItem('valoria_user')
    setUser(null)
    setMessage('Sesión cerrada.')
  }

  return (
    <main className="app-shell">
      <section className={`auth-layout ${isLogin ? 'login-mode' : 'register-mode'}`} aria-labelledby="page-title">
        <aside className="art-panel">
          <img src={valoriaTown} alt="Pueblo medieval de VALORIA" />
          <div className="art-copy">
            <p className="eyebrow">VALORIA</p>
            <p>Convierte tus hábitos<br />en misiones.</p>
          </div>
        </aside>

        <section className="auth-card">
          <div className="form-heading">
            <p className="eyebrow mobile-eyebrow">VALORIA</p>
            <h1 id="page-title">{user ? 'Tu aventura comienza' : isLogin ? 'Entrar al reino' : 'Crear cuenta'}</h1>
            {!user && (
              <p className="subtitle">
                {isLogin ? 'Continúa tu progreso y completa tus misiones.' : 'Regístrate para guardar tu progreso.'}
              </p>
            )}
          </div>

          {user ? (
            <div className="welcome-panel">
              <p>Bienvenido, aventurero.</p>
              <strong>{user.correo}</strong>
              <span>Rol: {user.rol}</span>
              <button className="secondary-button" type="button" onClick={logout}>Cerrar sesión</button>
            </div>
          ) : (
            <>
              <form onSubmit={handleSubmit} noValidate>
                <label htmlFor="correo">Correo electrónico</label>
                <input
                  id="correo"
                  name="correo"
                  type="email"
                  value={form.correo}
                  onChange={handleChange}
                  autoComplete="email"
                  placeholder="ana@test.com"
                  required
                />
                {errors.correo && <small className="field-error">{errors.correo}</small>}

                <label htmlFor="contrasena">Contraseña</label>
                <input
                  id="contrasena"
                  name="contrasena"
                  type="password"
                  value={form.contrasena}
                  onChange={handleChange}
                  autoComplete={isLogin ? 'current-password' : 'new-password'}
                  placeholder="Mínimo 8 caracteres"
                  required
                />
                {errors.contrasena && <small className="field-error">{errors.contrasena}</small>}

                <button className="primary-button" type="submit">
                  {isLogin ? 'Iniciar sesión' : 'Registrarme'}
                </button>
              </form>

              <div className="separator"><span>o</span></div>
              <button className="google-button" type="button" disabled title="Disponible próximamente">
                <span className="google-mark">G</span>
                Continuar con Google
              </button>

              <p className="switch-action">
                {isLogin ? '¿Aún no tienes cuenta?' : '¿Ya tienes una cuenta?'}
                <button type="button" onClick={() => changeMode(isLogin ? 'register' : 'login')}>
                  {isLogin ? 'Crear cuenta' : 'Iniciar sesión'}
                </button>
              </p>
            </>
          )}

          {message && <p className={errors.correo || errors.contrasena ? 'feedback error' : 'feedback'}>{message}</p>}
        </section>
      </section>
    </main>
  )
}

export default App
