package com.valoria.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank @Email String correo,
        @NotBlank @Size(min = 8) String contrasena
) {
}