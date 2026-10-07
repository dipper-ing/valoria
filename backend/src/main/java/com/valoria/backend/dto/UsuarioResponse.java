package com.valoria.backend.dto;

import com.valoria.backend.model.Role;
import com.valoria.backend.model.Usuario;

public record UsuarioResponse(Long id, String correo, Role rol) {

    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getCorreo(), usuario.getRol());
    }
}