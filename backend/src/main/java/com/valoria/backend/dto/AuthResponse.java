package com.valoria.backend.dto;

public record AuthResponse(String token, UsuarioResponse usuario) {
}