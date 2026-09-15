package com.valoria.backend.service;

import com.valoria.backend.dto.RegistroRequest;
import com.valoria.backend.model.Role;
import com.valoria.backend.model.Usuario;
import com.valoria.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrar(RegistroRequest request) {
        if (usuarioRepository.findByCorreo(request.correo()).isPresent()) {
            throw new IllegalArgumentException("El correo ya esta registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setCorreo(request.correo());
        usuario.setContrasenaHash(passwordEncoder.encode(request.contrasena()));
        usuario.setRol(Role.PLAYER);
        usuario.setProveedorAuth("LOCAL");

        return usuarioRepository.save(usuario);
    }
}