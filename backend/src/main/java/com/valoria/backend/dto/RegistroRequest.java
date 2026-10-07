package com.valoria.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato valido")
        String correo,

        @NotBlank(message = "La contrasena es obligatoria")
        @Size(min = 8, message = "La contrasena debe tener minimo 8 caracteres")
        String contrasena
) {
}