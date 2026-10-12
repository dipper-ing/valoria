package com.valoria.backend.service;

import com.valoria.backend.dto.AuthResponse;
import com.valoria.backend.dto.LoginRequest;
import com.valoria.backend.dto.RegistroRequest;
import com.valoria.backend.dto.UsuarioResponse;
import com.valoria.backend.exception.CorreoYaRegistradoException;
import com.valoria.backend.exception.CredencialesInvalidasException;
import com.valoria.backend.model.Role;
import com.valoria.backend.model.Usuario;
import com.valoria.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.findByCorreo(request.correo()).isPresent()) {
            throw new CorreoYaRegistradoException(request.correo());
        }

        Usuario usuario = new Usuario();
        usuario.setCorreo(request.correo());
        usuario.setContrasenaHash(passwordEncoder.encode(request.contrasena()));
        usuario.setRol(Role.PLAYER);
        usuario.setProveedorAuth("LOCAL");

        return construirRespuesta(usuarioRepository.save(usuario));
    }

    public AuthResponse autenticar(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.correo())
                .orElseThrow(CredencialesInvalidasException::new);

        if (usuario.getContrasenaHash() == null
                || !passwordEncoder.matches(request.contrasena(), usuario.getContrasenaHash())) {
            throw new CredencialesInvalidasException();
        }

        return construirRespuesta(usuario);
    }

    private AuthResponse construirRespuesta(Usuario usuario) {
        return new AuthResponse(jwtService.generarToken(usuario), UsuarioResponse.desde(usuario));
    }
}