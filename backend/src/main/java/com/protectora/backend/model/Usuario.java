package com.protectora.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    private String nombre;

    private String email;

    @Column(name = "contraseña")
    private String contrasena;

    @Column(name = "teléfono")
    private String telefono;

    @Column(name = "dirección")
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Enum interno
    public enum TipoUsuario {
        adoptante, voluntario, empleado, administrador
    }

    // Getters y setters
}
