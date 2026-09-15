package com.valoria.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name= "usuario")
public class Usuario {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getProveedorAuth() {
        return proveedorAuth;
    }

    public void setProveedorAuth(String proveedorAuth) {
        this.proveedorAuth = proveedorAuth;
    }

    public String getGoogleRefreshToken() {
        return googleRefreshToken;
    }

    public void setGoogleRefreshToken(String googleRefreshToken) {
        this.googleRefreshToken = googleRefreshToken;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(unique = true, nullable = false)
    private String correo;

    private String contrasenaHash;

    private String proveedorAuth;

    private String googleRefreshToken;

    @Enumerated(EnumType.STRING)
    private Role rol;
}