package com.valoria.backend.exception;

public class CorreoYaRegistradoException extends RuntimeException {

    public CorreoYaRegistradoException(String correo) {
        super("El correo " + correo + " ya esta registrado");
    }
}