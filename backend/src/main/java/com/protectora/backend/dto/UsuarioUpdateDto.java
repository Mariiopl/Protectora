package com.protectora.backend.dto;

import com.protectora.backend.model.Usuario;

public class UsuarioUpdateDto {
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private Usuario.TipoUsuario tipoUsuario;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario.TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Usuario.TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

}
